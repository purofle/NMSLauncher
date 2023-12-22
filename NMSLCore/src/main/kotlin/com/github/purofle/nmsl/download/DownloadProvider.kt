package com.github.purofle.nmsl.download

import com.github.purofle.nmsl.game.Manifest
import com.github.purofle.nmsl.utils.io.HttpRequest

abstract class DownloadProvider {
    abstract val versionListURL: String
    abstract val assetBaseURL: String
    abstract val mavenURL: String

    suspend fun getManifest() = HttpRequest.getJson<Manifest>(versionListURL)

    companion object {
        val providers = mapOf(
            "mcbbs" to MCBBSDownloadProvider(),
            "mojang" to MojangDownloadProvider(),
            "bmclapi" to BMCLAPIDownloadProvider()
        )
    }
}