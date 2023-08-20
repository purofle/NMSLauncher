package com.github.purofle.nmsl.download

import com.github.purofle.nmsl.game.Versions
import com.github.purofle.nmsl.utils.io.HttpRequest
import kotlinx.coroutines.flow.flow

abstract class DownloadProvider {
    abstract val versionListURL: String
    abstract val assetBaseURL: String
    abstract val mavenURL: String

    fun getVersionList() = flow {
        emit(HttpRequest.getJson<Versions>(versionListURL).versions)
    }
}