package com.github.purofle.nmsl.download.fabric

import com.github.purofle.nmsl.download.DownloadProvider
import com.github.purofle.nmsl.download.MCBBSDownloadProvider
import com.github.purofle.nmsl.game.Artifact
import com.github.purofle.nmsl.game.GameJson
import com.github.purofle.nmsl.game.Library
import com.github.purofle.nmsl.game.LibraryDownload
import com.github.purofle.nmsl.utils.io.HttpRequest
import com.github.purofle.nmsl.utils.io.HttpRequest.downloadFile
import com.github.purofle.nmsl.utils.json.JsonUtils.toJsonString
import com.github.purofle.nmsl.utils.os.OS
import org.apache.log4j.LogManager
import org.apache.log4j.Logger
import kotlin.io.path.createDirectories
import kotlin.io.path.div
import kotlin.io.path.writeText

class FabricDownloader(
    private val provider: DownloadProvider = MCBBSDownloadProvider(),
    private val version: GameJson,
) {

    private val versionDir = OS.minecraftWorkingDirectory() / "versions" / version.id
    private val librariesDir = OS.minecraftWorkingDirectory() / "libraries"

    private val gameJsonFile = versionDir / "${version.id}.json"

    private val logger: Logger = LogManager.getLogger(this::class.java)

    private lateinit var fabricMeta: FabricMeta

    private suspend fun downloadFabricMeta() {
        val fabricVersion = FabricVersion.getFabricVersion()
        fabricMeta = getFabricMeta(fabricVersion.first { it.gameVersion == version.id }.metaUrl)
    }

    suspend fun downloadFabric() {
        downloadFabricMeta()

        fabricMeta.loader.download()
        fabricMeta.intermediary.download()

        val libraries = fabricMeta.launcherMeta.libraries.common.map {
            val file = librariesDir.resolve(getPath(it.name))
            file.parent.createDirectories()
            HttpRequest.DownloadInfo(getLibraryUrl(maven = it.name), file.toFile(), it.sha1)
        }

        HttpRequest.downloadFiles(libraries, onDownloadComplete = {})
        logger.info("Downloaded libraries")
    }

    fun editGameJson() {

        val loader = Library(
            name = fabricMeta.loader.maven, downloads = LibraryDownload(
                Artifact(
                    path = getPath(fabricMeta.loader.maven),
                    sha1 = null,
                    size = 0,
                    url = getLibraryUrl(maven = fabricMeta.loader.maven)
                )
            )
        )

        val intermediary = Library(
            name = fabricMeta.intermediary.maven, downloads = LibraryDownload(
                Artifact(
                    path = getPath(fabricMeta.intermediary.maven),
                    sha1 = null,
                    size = 0,
                    url = getLibraryUrl(maven = fabricMeta.intermediary.maven)
                )
            )
        )

        val newVersion = version.copy(
            mainClass = fabricMeta.launcherMeta.mainClass["client"] ?: error("mainClass not found"),
            libraries = version.libraries + loader + intermediary + fabricMeta.launcherMeta.libraries.common.map {
                Library(
                    name = it.name,
                    downloads = LibraryDownload(
                        Artifact(
                            path = getPath(it.name),
                            sha1 = it.sha1,
                            size = 0,
                            url = getLibraryUrl(maven = it.name)
                        )
                    )
                )
            }
        )
        gameJsonFile.writeText(newVersion.toJsonString())
    }

    suspend fun Maven.download() {
        val path = librariesDir.resolve(getPath(maven))
        path.parent.createDirectories()

        val url = getLibraryUrl(maven = maven)
        downloadFile {
            this.url = url
            saveFile = path.toFile()
        }
    }
}