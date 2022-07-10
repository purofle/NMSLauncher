package com.github.purofle.nmsl.download

import com.github.purofle.nmsl.utils.json.JsonUtils.toAssetList
import com.github.purofle.nmsl.utils.json.JsonUtils.toJsonObject
import com.google.gson.JsonElement
import kotlinx.coroutines.flow.flow
import java.io.File

interface DownloadProvider {
    val versionListURL: String
    val assetBaseURL: String

    companion object {
        fun readVersionList(
            file: String,
        ) = flow {
            emit(File(file).readText().toJsonObject<JsonElement>().toAssetList())
        }
    }
}