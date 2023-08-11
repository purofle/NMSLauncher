package com.github.purofle.nmsl.download

import com.github.purofle.nmsl.game.Artifact
import com.github.purofle.nmsl.game.Asset
import com.github.purofle.nmsl.game.GameJson
import com.github.purofle.nmsl.game.Library
import com.github.purofle.nmsl.game.version.Version
import com.github.purofle.nmsl.utils.HashUtils
import com.github.purofle.nmsl.utils.io.HttpRequest.client
import com.github.purofle.nmsl.utils.io.HttpRequest.getJson
import com.github.purofle.nmsl.utils.io.downloadFile
import com.github.purofle.nmsl.utils.json.JsonUtils.assetsToAssets
import com.github.purofle.nmsl.utils.json.JsonUtils.toJsonString
import com.github.purofle.nmsl.utils.os.OperatingSystem
import com.github.purofle.nmsl.utils.os.OperatingSystem.Companion.getMinecraftWorkingDirectory
import kotlinx.serialization.json.JsonObject
import org.apache.logging.log4j.LogManager
import java.io.File
import java.nio.file.Path
import java.security.MessageDigest
import java.util.stream.Collectors
import java.util.zip.ZipFile
import kotlin.io.path.*

class DownloadGame(
    private val downloadProvider: DownloadProvider,
    private val version: Version
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

    /**
     * 获取本机需要的所有依赖
     */
    fun getAllLibrary() = gameJson.libraries.filter { gameLibrariesFilter(it) }

    suspend fun downloadLibrary(artifact: Artifact) {
        val logger = LogManager.getLogger("downloadLibrary")
        logger.info("check download: ${artifact.url}")
        val lib = getMinecraftWorkingDirectory("libraries") / artifact.path
        lib.parent.createDirectories()
        if (lib.isRegularFile()) {
            val sha1 = checkSha1(lib)
            if (sha1 == artifact.sha1) {
                logger.info("${artifact.path}检验通过")
                checkNatives(lib, artifact)
                return
            }
        }

        logger.info("start download: ${lib.name}")
        client.downloadFile(
            lib.toFile(),
            artifact.url.replace("https://libraries.minecraft.net/", downloadProvider.mavenURL)
        ) {
            logger.info("download done: ${lib.name}")
        }

    }

    private fun checkSha1(lib: Path) = HashUtils.getCheckSumFromFile(
        MessageDigest.getInstance("SHA-1"),
        lib.toFile()
    )

    private fun checkNatives(lib: Path, artifact: Artifact) {
        val logger = LogManager.getLogger("downloadLibrary")
        val natives = versionDir / "natives"
        if (!natives.isDirectory()) {
            natives.createDirectories()
        }

        if (artifact.path.contains("natives")) {
            logger.warn("wait extract: ${lib.name}")
            unpackJar(lib, natives)
            logger.info("extract: ${lib.name}")
        }
    }

    /**
     * 把文件中的 jar/so 提取到同一个目录.
     * @param path 文件目录
     * @param out 输出目录
     */
    private fun unpackJar(path: Path, out: Path) {
        val zip = ZipFile(path.toFile())
        zip.stream().collect(Collectors.toList()).forEach {
            val entry = out.resolve(it.name)
            // get all so or dll files
            if (entry.extension == "so" || entry.extension == "dll") {
                var newPath: Path = entry.parent
                if (entry.parent != out) {
                    while (true) { // 用来把 lib 放到一个文件夹
                        newPath = newPath.parent
                        if (newPath == out) { // 让放置的目录跟输出目录一致
                            break
                        }
                    }
                }
                newPath /= entry.name // 给放置的文件加上文件名
                newPath.parent.createDirectories()
                zip.getInputStream(it).use { input ->
                    newPath.toFile().outputStream().use { output ->
                        input.copyTo(output)
                    }
                }
            }
        }
    }

    /**
     * 用于过滤所需的依赖
     */
    private fun gameLibrariesFilter(library: Library): Boolean {
        return if (library.rules.isNullOrEmpty()) {
            true // 非原生库，应为 [true]
        } else {
            versionFilter(library)
        }
    }

    private fun versionFilter(library: Library): Boolean {
        if (library.rules?.size == 1) {
            // 只有 allow 的情况
            library.rules[0].let {
                return (it.action == "allow") and (it.os?.name == OperatingSystem.CURRENT_OS.checkedName)
            }
        } else {
            return !library.rules!!.any { (it.action == "disallow") and (it.os?.name == OperatingSystem.CURRENT_OS.checkedName) }
        }
//        return if (library.rules?.size == 1) { // 新版本只有一个规则
//            if (library.name.contains("aarch_64")) {
//                false
//            } else
//                (library.rules[0].action == "allow") && (library.rules[0].os?.name == OperatingSystem.CURRENT_OS.checkedName)
//        } else { // 老版本有多个规则
//            return library.rules?.any { it.action == "disallow" && it.os?.name == OperatingSystem.CURRENT_OS.name } != true
//        }
    }

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
            assetDir.toFile(),
            url
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
            log4j2File.toFile(),
            gameJson.logging.client.file.url
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
                    clientFile.toFile(),
                    url
                ) {
                    logger.info("download done: ${version.id}.jar")
                }
            }
        }
    }

    fun generateCommand(): String {
        val libs = getMinecraftWorkingDirectory("libraries").toFile().listAllFile()
        val logger = LogManager.getLogger("generateCommand")
        var cp = libs.map { it.absolutePath }.reduce { acc, file ->
            "${acc}:${file}"
        }
        cp += ":${versionDir / "${version.id}.jar"}"
        val command =
            "/usr/bin/java -Xss1M -Djava.library.path=${versionDir / "natives"} -Dminecraft.launcher.brand=NMSLauncher -Dminecraft.launcher.version=2.1.3674 -cp $cp -Xmx2G -XX:+UnlockExperimentalVMOptions -XX:+UseG1GC -XX:G1NewSizePercent=20 -XX:G1ReservePercent=20 -XX:MaxGCPauseMillis=50 -XX:G1HeapRegionSize=32M -Dlog4j.configurationFile=${versionDir / gameJson.logging.client.file.id} net.minecraft.client.main.Main --username purofle --version ${gameJson.id} --gameDir ${versionDir.pathString} --assetsDir ${assetsDir.pathString} --assetIndex ${gameJson.id} --uuid 0000000000000000 --accessToken 0000000000000000 --userType mojang --versionType release"
        logger.info(command)
        return command
    }


    private var files: MutableList<File> = mutableListOf()
    private fun File.listAllFile(): List<File> {
        this.listFiles()!!.forEach {
            if (it.isDirectory) {
                it.listAllFile()
            } else {
                files.add(it)
            }
        }
        return files.toList()
    }
}