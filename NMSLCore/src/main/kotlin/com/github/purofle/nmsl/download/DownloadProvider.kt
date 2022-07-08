package com.github.purofle.nmsl.download

interface DownloadProvider {
    val versionListURL: String
    val assetBaseURL: String
}