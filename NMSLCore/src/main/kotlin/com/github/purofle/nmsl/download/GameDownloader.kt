package com.github.purofle.nmsl.download

import com.github.purofle.nmsl.auth.microsoft.MicrosoftAuth.CLIENT_ID
import com.github.purofle.nmsl.config.Config
import com.github.purofle.nmsl.game.Argument
import com.github.purofle.nmsl.game.Asset
import com.github.purofle.nmsl.game.GameJson
import com.github.purofle.nmsl.game.Version
import com.github.purofle.nmsl.utils.io.HttpRequest
import com.github.purofle.nmsl.utils.io.HttpRequest.downloadFile
import com.github.purofle.nmsl.utils.json.JsonUtils.decode
import com.github.purofle.nmsl.utils.json.JsonUtils.toJsonObject
import com.github.purofle.nmsl.utils.json.JsonUtils.toJsonString
import com.github.purofle.nmsl.utils.os.OS
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.util.zip.ZipFile
import kotlin.io.path.*

/**
 * Download Game for [provider]
 */
class GameDownloader(
    private val provider: DownloadProvider = MCBBSDownloadProvider(),
    private val version: Version,
) {
    private val versionDir = OS.minecraftWorkingDirectory() / "versions" / version.id
    private val nativesDir = versionDir / "natives"
    private val librariesDir = OS.minecraftWorkingDirectory() / "libraries"
    private val assetsDir = OS.minecraftWorkingDirectory() / "assets"

    private val gameJsonFile = versionDir / "${version.id}.json"
    private lateinit var gameJson: GameJson
    private lateinit var assetIndex: JsonElement

    private val logger: Logger = LogManager.getLogger(this::class.java)

    init {
        versionDir.createDirectories()
        nativesDir.createDirectories()
        librariesDir.createDirectories()
        assetsDir.createDirectories()
        assetsDir.resolve("indexes").createDirectories()

        if (gameJsonFile.isReadable()) {
            gameJson = gameJsonFile.readText().toJsonObject()
            assetIndex = assetsDir.resolve("indexes/${gameJson.assetIndex.id}.json").readText().toJsonObject()
        }
    }

    /**
     * Download game json
     */
    suspend fun downloadGameJson(save: Boolean = true): GameJson {
        logger.info("Downloading game json")
        val json: GameJson = version.url.let { HttpRequest.getJson(it) }
        assetIndex = HttpRequest.getJson(json.assetIndex.url)

        gameJson = json

        if (!save) {
            return json
        }

        gameJsonFile.writeText(json.toJsonString())
        assetsDir.resolve("indexes/${json.assetIndex.id}.json").writeText(assetIndex.toJsonString())

        return json
    }

    suspend fun downloadLibrary() {
        logger.info("Downloading libraries")
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
        logger.info("Downloaded libraries")
    }

    suspend fun downloadAssets() {

        logger.info("Downloading assets")

        val assets = assetIndex.jsonObject["objects"]?.jsonObject?.map {
            it.key to it.value.decode<Asset>()
        }?.toMap() ?: throw Exception("assets not found")

        val assetsDownloadInfo = assets.map {
            val hash = it.value.hash
            val first2Hash = hash.substring(0, 2)
            val dir = assetsDir.resolve("objects/$first2Hash") //hash的前两位
            dir.createDirectories()
            HttpRequest.DownloadInfo(
                "${provider.assetBaseURL}/$first2Hash/${it.value.hash}",
                dir.resolve(it.value.hash).toFile(),
                it.value.hash
            )
        }

        HttpRequest.downloadFiles(assetsDownloadInfo) { url, bytesSentTotal, contentLength ->
//            logger.debug("downloading $url: $bytesSentTotal/$contentLength")
        }

        logger.info("Downloaded assets")
    }

    suspend fun downloadClientJar() {

        logger.info("Downloading client jar")

        val clientJar = versionDir.resolve("${version.id}.jar")

        downloadFile {
            gameJson.downloads.let {
                url = it.client.url
                sha1 = it.client.sha1
            }
            saveFile = clientJar.toFile()

            onProgress { bytesSentTotal, contentLength ->
                logger.debug("Downloaded: $bytesSentTotal/$contentLength")
            }
        }

        logger.info("Downloaded client jar")
    }

    fun extraNatives() {
        val natives =
            gameJson.serializerLibrary().filter { it.path.contains("natives") }.map { librariesDir.resolve(it.path) }

        natives.forEach {
            // 解压内部的文件
            ZipFile(it.toFile()).use { zip ->
                // 解压除了 sha1 跟 git 后缀的文件
                zip.entries().asSequence()
                    .filterNot { entry -> entry.isDirectory }
                    .filterNot { entry -> entry.name.contains("META-INF") }
                    .filterNot { entry ->
                        entry.name.endsWith(".sha1") && entry.name.endsWith(".git")
                    }
                    .forEach { entry ->
                        zip.getInputStream(entry).use { input ->
                            val path = nativesDir.resolve(versionDir.resolve(entry.name).fileName)
                            path.writeBytes(input.readAllBytes())
                        }
                    }
            }
        }
    }

    fun getLauncherArgument(): List<String> {

        val config = Config.config

        val jvmArgument = Argument.parseJvmArgument(
            nativesDir,
            artifacts = gameJson.serializerLibrary(),
            clientPath = versionDir.resolve("${version.id}.jar")
        )
        val gameArgument = Argument.GameArgument(
            username = config.profile.minecraftProfile.name,
            assetIndex = gameJson.assetIndex.id,
            accessToken = config.profile.minecraftAccessToken,
            uuid = config.profile.minecraftProfile.id,
            clientId = CLIENT_ID,
            xuid = config.profile.xstsToken
        )

        return jvmArgument + gameJson.mainClass + gameArgument.parseGameArgument()
    }
}