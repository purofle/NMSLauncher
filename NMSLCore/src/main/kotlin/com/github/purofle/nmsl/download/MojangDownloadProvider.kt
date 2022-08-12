package com.github.purofle.nmsl.download

class MojangDownloadProvider: DownloadProvider {
    override val versionListURL: String = "https://launchermeta.mojang.com/mc/game/version_manifest_v2.json"
    override val assetBaseURL: String = "https://resources.download.minecraft.net/"
    override val mavenURL: String = "https://libraries.minecraft.net/"
}