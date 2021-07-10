package com.github.purofle.nmsl.game

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import java.io.File

/**
 * 下载游戏相关文件
 * @param path 文件下载目录
 * @param logLevel log 输出等级
 */
class GameDownload(
    path: File,
    logLevel: LogLevel
) {
    private val versionPath = File(path, "version_manifest.json")
    private val client = HttpClient(CIO) {
        install(Logging) {
            logger = Logger.DEFAULT
            level = logLevel
        }
    }

    /**
     * 下载并保存
     */
    suspend fun getVersionManifest(): McVersionManifest {
        val resp = client.get<McVersionManifest>(
            "https://launchermeta.mojang.com/mc/game/version_manifest.json"
        )
        versionPath.writeText(resp.toString())
        return resp
    }
}