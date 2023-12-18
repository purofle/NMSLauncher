package com.github.purofle.nmsl.download

import com.github.purofle.nmsl.game.GameJson
import com.github.purofle.nmsl.game.Version
import com.github.purofle.nmsl.utils.io.HttpRequest
import com.github.purofle.nmsl.utils.json.JsonUtils.toJsonString
import com.github.purofle.nmsl.utils.os.OS
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import kotlin.io.path.createDirectories
import kotlin.io.path.div
import kotlin.io.path.isWritable
import kotlin.io.path.writeText

/**
 * Download Game for [provider]
 */
class GameDownloader(
    private val provider: DownloadProvider,
    private val version: Version,
) {
    private val versionDir = OS.minecraftWorkingDirectory() / "versions" / version.id
    private val librariesDir = OS.minecraftWorkingDirectory() / "libraries"
    private lateinit var gameJson: GameJson

    private val logger: Logger = LogManager.getLogger(this::class.java)

    /**
     * Download game json
     */
    suspend fun downloadGameJson(save: Boolean = true): GameJson {
        val json: GameJson = version.url.let { HttpRequest.getJson(it) }
        val jsonFile = versionDir.resolve("${version.id}.json")

        gameJson = json

        if (!save) {
            return json
        }

        with(jsonFile) {
            if (isWritable()) {
                writeText(json.toJsonString())
            } else {
                parent.createDirectories()
                writeText(json.toJsonString())
            }
        }
        return json
    }

    suspend fun downloadLibrary() {
        val libraries = gameJson.serializerLibrary().map {
            val file = librariesDir.resolve(it.path).toFile()
            if (!file.canWrite()) {
                file.parentFile.mkdirs()
            }
            HttpRequest.DownloadInfo(it.url, file, it.sha1)
        }
        HttpRequest.downloadFiles(libraries) { url, bytesSentTotal, contentLength ->
            logger.debug("Downloaded $url: $bytesSentTotal/$contentLength")
        }
    }
}