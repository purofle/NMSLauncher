package com.github.purofle.nmsl.game

import com.github.purofle.nmsl.utils.check
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import java.io.File
import java.nio.file.Path
import java.nio.file.Paths

class GameDownload(
    path: String
) {
    val versionPath = File(Paths.get(path, "version").toString())

    val client = HttpClient(CIO) {
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
        }
    }
    init {
        File(path).check(true)
    }
    suspend fun getVersionManifest() {
        val resp = client.get<String>(
            "https://launchermeta.mojang.com/mc/game/version_manifest.json"
        )
        versionPath.writeText(resp)
    }
}