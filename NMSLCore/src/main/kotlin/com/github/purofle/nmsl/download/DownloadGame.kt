package com.github.purofle.nmsl.download

import com.github.purofle.nmsl.game.Artifact
import com.github.purofle.nmsl.game.Asset
import com.github.purofle.nmsl.game.GameJson
import com.github.purofle.nmsl.game.Version
import com.github.purofle.nmsl.utils.HashUtils
import com.github.purofle.nmsl.utils.io.HttpRequest.client
import com.github.purofle.nmsl.utils.io.HttpRequest.getJson
import com.github.purofle.nmsl.utils.io.downloadFile
import com.github.purofle.nmsl.utils.json.JsonUtils.assetsToAssets
import com.github.purofle.nmsl.utils.json.JsonUtils.toJsonString
import com.github.purofle.nmsl.utils.os.OperatingSystem
import com.github.purofle.nmsl.utils.os.OperatingSystem.*
import com.github.purofle.nmsl.utils.os.OperatingSystem.Companion.getMinecraftWorkingDirectory
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import org.apache.logging.log4j.LogManager
import java.nio.file.Path
import java.security.MessageDigest
import kotlin.io.path.*

class DownloadGame(
    private val downloadProvider: DownloadProvider, private val version: Version
) {

    private val versionDir = getMinecraftWorkingDirectory() / "versions" / version.id
    private val assetsDir = getMinecraftWorkingDirectory() / "assets"
    private lateinit var gameJson: GameJson
    private lateinit var assetsJson: JsonObject

    init {
        createDirs()
    }

    /**
     * 创建游戏目录
     */
    private fun createDirs() {
        listOf("libraries", "assets", "versions").forEach {
            getMinecraftWorkingDirectory(it).createDirectories()
        }

        listOf("indexes", "objects").forEach {
            getMinecraftWorkingDirectory("assets").resolve(it).createDirectories()
        }
        versionDir.createDirectories()
    }

    private fun getAllLibrary(): List<Artifact> {
        val library = gameJson.libraries.filter { it.checkRules() }.filter { it.checkArch() }
        val artifacts = library.mapNotNull { it.downloads.artifact }
        val classifiers = library.mapNotNull { it.downloads.classifiers }

        val sources = classifiers.map {
            when (OperatingSystem.CURRENT_OS) {
                WINDOWS -> it.nativesWindows!!
                LINUX -> it.nativesLinux!!
                OSX -> it.nativesOsx!!
                UNKNOWN -> throw InternalError("your dick boom")
            }
        }.map {
            it
        }
        return sources + artifacts
    }

    suspend fun downloadAllLibrary() {
        getAllLibrary().forEach {
            downloadLibrary(it)
        }
    }

    private suspend fun downloadLibrary(artifact: Artifact) {
        val logger = LogManager.getLogger("downloadLibrary")
        logger.info("check download: ${artifact.url}")
        val lib = getMinecraftWorkingDirectory("libraries") / artifact.path
        lib.parent.createDirectories()
        if (lib.isRegularFile()) {
            val sha1 = checkSha1(lib)
            if (sha1 == artifact.sha1) {
                logger.info("${artifact.path}检验通过")
                return
            }
        }

        logger.info("start download: ${lib.name}")
        client.downloadFile(
            lib.toFile(), artifact.url.replace("https://libraries.minecraft.net/", downloadProvider.mavenURL)
        ) {
            logger.info("download done: ${lib.name}")
        }
    }

    private fun checkSha1(lib: Path) = HashUtils.getCheckSumFromFile(
        MessageDigest.getInstance("SHA-1"), lib.toFile()
    )

    /**
     * 下载游戏 json
     */
    suspend fun downloadJson() {
        gameJson = getJson(version.url)
        (versionDir / "${version.id}.json").writeText(gameJson.toJsonString())
    }

    suspend fun downloadAssetJson(): List<Asset> {
        assetsJson = getJson(gameJson.assetIndex.url)
        (assetsDir / "indexes" / "${version.id}.json").writeText(assetsJson.toJsonString())
        return assetsToAssets(assetsJson)
    }

    suspend fun downloadAsset(asset: Asset) {

        val logger = LogManager.getLogger("downloadAsset")
        val assetDir = assetsDir / "objects" / asset.hash.substring(0, 2) / asset.hash
        val url = "${downloadProvider.assetBaseURL}${asset.hash.substring(0, 2)}/${asset.hash}"

        assetDir.parent.createDirectories()
        if (assetDir.isRegularFile()) {
            val sha1 = checkSha1(assetDir)
            if (sha1 == asset.hash) {
                logger.info("${asset.hash}检验通过")
                return
            }
        }
        logger.info("start download: ${asset.hash}")
        client.downloadFile(
            assetDir.toFile(), url
        ) {
            logger.info("download done: ${asset.hash}")
        }
    }

    suspend fun downloadClient() {
        val logger = LogManager.getLogger("downloadClient")
        val url = gameJson.downloads.client.url
        val clientFile = versionDir / "${version.id}.jar"
        val log4j2File = versionDir / gameJson.logging.client.file.id

        if (log4j2File.isRegularFile()) {
            val sha1 = checkSha1(log4j2File)
            if (sha1 == gameJson.logging.client.file.sha1) {
                logger.info("${gameJson.logging.client.file.id}检验通过")
                return
            }
        }

        logger.info("start download: ${gameJson.logging.client.file.id}")
        client.downloadFile(
            log4j2File.toFile(), gameJson.logging.client.file.url
        ) {
            logger.info("download done: ${gameJson.logging.client.file.id}")
        }

        if (clientFile.isRegularFile()) {
            val sha1 = checkSha1(clientFile)
            if (sha1 == gameJson.downloads.client.sha1) {
                logger.info("${version.id}.jar检验通过")
                return
            } else {
                logger.info("start download: ${version.id}.jar")
                client.downloadFile(
                    clientFile.toFile(), url
                ) {
                    logger.info("download done: ${version.id}.jar")
                }
            }
        }
    }

    private fun argumentParse(): String {
        if (gameJson.arguments == null) {
            return gameJson.minecraftArguments!!
        }
        val sb = StringBuilder()
        gameJson.arguments!!.jvm.forEach {
            val jsonPrimitive = it as? JsonPrimitive
            jsonPrimitive?.let { primitive ->
                sb.append(primitive.content)
                sb.append(" ")
            }
        }
        gameJson.arguments!!.game.forEach {
            val jsonPrimitive = it as? JsonPrimitive
            jsonPrimitive?.let { primitive ->
                sb.append(primitive.content)
                sb.append(" ")
            }
        }
        return sb.toString()
    }

    fun generateCommand(): String {
        val logger = LogManager.getLogger("generateCommand")
        val gameArgument = argumentParse()
        logger.debug(gameArgument)
        return argumentParse()
    }
}