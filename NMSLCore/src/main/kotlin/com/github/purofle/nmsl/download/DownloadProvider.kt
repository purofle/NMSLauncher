package com.github.purofle.nmsl.download

import com.github.purofle.nmsl.game.VersionList
import com.github.purofle.nmsl.utils.io.HttpRequest
import kotlinx.coroutines.flow.flow

interface DownloadProvider {
    val versionListURL: String
    val assetBaseURL: String

    fun getVersionList() = flow {
        emit(HttpRequest.getJson<VersionList>(versionListURL).versions)
    }
}