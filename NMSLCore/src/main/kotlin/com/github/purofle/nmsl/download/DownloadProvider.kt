package com.github.purofle.nmsl.download

import com.github.purofle.nmsl.game.version.Versions
import com.github.purofle.nmsl.utils.io.HttpRequest
import kotlinx.coroutines.flow.flow

interface DownloadProvider {
    val versionListURL: String
    val assetBaseURL: String
    val mavenURL: String

    fun getVersionList() = flow {
        emit(HttpRequest.getJson<Versions>(versionListURL).versions)
    }
}