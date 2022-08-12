package com.github.purofle.nmsl.download

open class BMCLAPIDownloadProvider(apiRoot: String = "https://bmclapi2.bangbang93.com"): DownloadProvider {
    override val versionListURL = "$apiRoot/mc/game/version_manifest_v2.json"
    override val assetBaseURL = "$apiRoot/assets/"
    override val mavenURL = "$apiRoot/maven/"
}