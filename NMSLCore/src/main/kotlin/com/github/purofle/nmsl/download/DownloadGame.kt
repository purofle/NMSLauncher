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
        val lib = getWorkingDirectory("libraries") / artifact.path
        lib.parent.createDirectories()
        if (lib.isRegularFile()) {
            val sha1 = checkSha1(lib)
            if (sha1 == artifact.sha1) {
                logger.info("${artifact.path}检验通过")
                return
            }
        }

        println("start download: ${lib.name}")
        client.downloadFile(
            lib.toFile(),
            artifact.url
        ) {
            logger.info("download done: ${lib.name}")
        }
        if (artifact.url.contains("natives")) {
            logger.warn("wait extract: ${lib.name}")
        }
    }

    private fun checkSha1(lib: Path) = HashUtils.getCheckSumFromFile(
        MessageDigest.getInstance("SHA-1"),
        lib.toFile()
    )

    fun unpackJar(path: Path) {

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