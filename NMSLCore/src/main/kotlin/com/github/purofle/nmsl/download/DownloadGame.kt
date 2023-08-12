package com.github.purofle.nmsl.download

import com.github.purofle.nmsl.game.*
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
        (assetsDir / "indexes" / "${gameJson.assets}.json").writeText(assetsJson.toJsonString())
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
            }
        } else {
            logger.info("start download: ${gameJson.logging.client.file.id}")
            client.downloadFile(
                log4j2File.toFile(), gameJson.logging.client.file.url
            ) {
                logger.info("download done: ${gameJson.logging.client.file.id}")
            }
        }

        if (clientFile.isRegularFile()) {
            val sha1 = checkSha1(clientFile)
            if (sha1 == gameJson.downloads.client.sha1) {
                logger.info("${version.id}.jar检验通过")
                return
            }
        } else {
            logger.info("start download: ${version.id}.jar")
            client.downloadFile(
                clientFile.toFile(), url
            ) {
                logger.info("download done: ${version.id}.jar")
            }
        }
    }

    fun generateCommand(): String {
        val logger = LogManager.getLogger("generateCommand")
        val jvmArgument = Argument.parseJvmArgument(
            versionDir / "natives",
            artifacts = getAllLibrary(),
            clientPath = versionDir / "${version.id}.jar"
        )
        val gameArgument = Argument.parseGameArgument(
            username = "purofle",
            assetIndex = gameJson.assets,
            accessToken = "111",
            clientId = "111",
            xuid = "111"
        )
        logger.debug("java $jvmArgument ${gameJson.mainClass} $gameArgument")
        return "java $jvmArgument ${gameJson.mainClass} $gameArgument"
    }
}