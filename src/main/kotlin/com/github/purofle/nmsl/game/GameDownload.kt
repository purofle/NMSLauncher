package com.github.purofle.nmsl.game

import com.github.purofle.nmsl.utils.check
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import java.io.File

class GameDownload(
    path: File
) {
    private val versionPath = File(path, "version")

    private val client = HttpClient(CIO) {
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.BODY
        }
    }
    init {
        path.check(true)
    }
    suspend fun getVersionManifest(): McVersionManifest {
        val resp = client.get<McVersionManifest>(
            "https://launchermeta.mojang.com/mc/game/version_manifest.json"
        )
        versionPath.writeText(resp.toString())
        return resp
    }
}