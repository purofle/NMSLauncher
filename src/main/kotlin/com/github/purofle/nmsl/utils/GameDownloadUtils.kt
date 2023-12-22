package com.github.purofle.nmsl.utils

import com.github.purofle.nmsl.config.Config
import com.github.purofle.nmsl.download.DownloadProvider
import com.github.purofle.nmsl.download.GameDownloader
import com.github.purofle.nmsl.game.GameManager
import java.io.File

fun getGameDownloader(gameVersion: String): GameDownloader {
    val config = Config.config
    val provider = DownloadProvider.providers[config.launcherConfig.provider] ?: error("Provider not found")
    val manifest = GameManager.getManifest()
    return GameDownloader(provider, manifest.versions.first { it.id == gameVersion })
}

fun startGame(launcherArgument: List<String>) {
    println(launcherArgument.joinToString(" "))
    val sh = File.createTempFile("nmsl", "sh")
    sh.writeText(launcherArgument.joinToString(" "))
    sh.deleteOnExit()

    ProcessBuilder(
        "/bin/sh", sh.absolutePath
    )
        .redirectOutput(ProcessBuilder.Redirect.INHERIT)
        .redirectError(ProcessBuilder.Redirect.INHERIT)
        .start()
        .waitFor()
}