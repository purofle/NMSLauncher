package com.github.purofle.nmsl.utils

import com.github.purofle.nmsl.config.Config
import com.github.purofle.nmsl.download.DownloadProvider
import com.github.purofle.nmsl.download.GameDownloader
import com.github.purofle.nmsl.game.GameManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

fun DownloadProvider.Companion.getDefaultProvider(): DownloadProvider {
    val config = Config.config
    return providers[config.launcherConfig.provider] ?: error("Provider not found")
}

fun getGameDownloader(gameVersion: String): GameDownloader {
    val provider = DownloadProvider.getDefaultProvider()
    val manifest = GameManager.getLocalManifest()
    return GameDownloader(provider, manifest.versions.first { it.id == gameVersion })
}

suspend fun startGame(version: String, launcherArgument: List<String>) {

    with(getGameDownloader(version)) {
        downloadLibrary()
        downloadAssets()
        downloadClientJar()
    }

    println(launcherArgument.joinToString(" "))
    withContext(Dispatchers.IO) {
        val sh = File.createTempFile("nmsl", "sh")
        sh.writeText("java " + launcherArgument.joinToString(" "))
        sh.deleteOnExit()

        ProcessBuilder(
            "/bin/sh", sh.absolutePath
        )
            .redirectOutput(ProcessBuilder.Redirect.INHERIT)
            .redirectError(ProcessBuilder.Redirect.INHERIT)
            .start()
            .waitFor()
    }
}