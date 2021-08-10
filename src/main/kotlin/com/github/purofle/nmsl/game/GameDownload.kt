package com.github.purofle.nmsl.game

import com.github.purofle.nmsl.game.version.Version
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.File

/**
 * 加载游戏相关文件
 * @param logLevel log 输出等级
 */
class GameDownload(
    logLevel: LogLevel
) {
    private val client = HttpClient(CIO) {
        install(Logging) {
            logger = Logger.DEFAULT
            level = logLevel
        }
        install(JsonFeature) {
            serializer = KotlinxSerializer()
        }
    }

    /**
     * 下载并保存
     */
    suspend fun getVersionManifest(): McVersionManifest {
        return client.get(
            "https://launchermeta.mojang.com/mc/game/version_manifest.json"
        )
    }

    /**
     * 下载版本信息
     */
    suspend fun getVersionInfo(url: String): Version {
        val json = Json { ignoreUnknownKeys = true }
        return json.decodeFromString(client.get(url))
    }
}