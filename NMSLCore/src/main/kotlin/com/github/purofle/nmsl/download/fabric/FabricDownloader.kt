package com.github.purofle.nmsl.download.fabric

import com.github.purofle.nmsl.download.DownloadProvider
import com.github.purofle.nmsl.download.MCBBSDownloadProvider
import com.github.purofle.nmsl.game.GameJson
import com.github.purofle.nmsl.utils.io.HttpRequest
import com.github.purofle.nmsl.utils.io.HttpRequest.downloadFile
import com.github.purofle.nmsl.utils.os.OS
import org.apache.log4j.LogManager
import org.apache.log4j.Logger
import kotlin.io.path.createDirectories
import kotlin.io.path.div

class FabricDownloader(
    private val provider: DownloadProvider = MCBBSDownloadProvider(),
    private val version: GameJson,
) {

    private val versionDir = OS.minecraftWorkingDirectory() / "versions" / version.id
    private val librariesDir = OS.minecraftWorkingDirectory() / "libraries"

    private val gameJsonFile = versionDir / "${version.id}.json"

    private val logger: Logger = LogManager.getLogger(this::class.java)

    suspend fun downloadFabric() {
        val fabricVersion = FabricVersion.getFabricVersion()
        val fabricMeta = getFabricMeta(fabricVersion.first { it.gameVersion == version.id }.metaUrl)

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

    suspend fun editGameJson() {
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