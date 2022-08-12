package com.github.purofle.nmsl.download

import com.github.purofle.nmsl.game.Artifact
import com.github.purofle.nmsl.game.GameJson
import com.github.purofle.nmsl.game.Library
import com.github.purofle.nmsl.game.version.Version
import com.github.purofle.nmsl.utils.HashUtils
import com.github.purofle.nmsl.utils.io.HttpRequest.client
import com.github.purofle.nmsl.utils.io.HttpRequest.getJson
import com.github.purofle.nmsl.utils.io.downloadFile
import com.github.purofle.nmsl.utils.json.JsonUtils.toJsonString
import com.github.purofle.nmsl.utils.os.OperatingSystem
import com.github.purofle.nmsl.utils.os.OperatingSystem.Companion.getWorkingDirectory
import org.apache.logging.log4j.LogManager
import java.nio.file.Path
import java.security.MessageDigest
import java.util.stream.Collectors
import java.util.zip.ZipFile
import kotlin.io.path.*

class DownloadGame(
    private val downloadProvider: DownloadProvider,
    private val version: Version
) {

    private val versionDir = getWorkingDirectory() / "versions" / version.id
    private lateinit var gameJson: GameJson

    init {
        createDirs()
    }

    /**
     * 创建游戏目录
     */
    fun createDirs() {
        listOf("libraries", "assets", "versions").forEach {
            getWorkingDirectory(it).createDirectories()
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
        val lib = getWorkingDirectory("libraries") / artifact.path
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
    fun unpackJar(path: Path, out: Path) {
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
            oldVersionFilter(library)
        }
    }

    private fun oldVersionFilter(library: Library): Boolean {
        return if (library.rules?.size == 1) { // 新版本只有一个规则
            if (library.name.contains("aarch_64")) {
                false
            } else
                (library.rules[0].action == "allow") && (library.rules[0].os?.name == OperatingSystem.CURRENT_OS.checkedName)
        } else { // 老版本有多个规则
            true // 先不适配老版本
        }
    }

    /**
     * 下载游戏 json
     */
    suspend fun downloadJson() {
        gameJson = getJson(version.url)
        (versionDir / "${version.id}.json").writeText(gameJson.toJsonString())
    }
}