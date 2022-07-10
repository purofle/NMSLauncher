package com.github.purofle.nmsl.download

import com.github.purofle.nmsl.game.Asset
import com.github.purofle.nmsl.game.Library
import com.github.purofle.nmsl.utils.io.HttpRequest
import com.github.purofle.nmsl.utils.json.JsonUtils.toAssetList
import com.google.gson.JsonElement
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class Downloader(
    private val provider: DownloadProvider,
    rootPath: String
    ) {

    fun downloadLibrary(libraries: List<Library>, context: CoroutineDispatcher = Dispatchers.IO) {
        libraries.forEach {

        }
    }
    private suspend fun getVersionList(): List<Asset> {
        return HttpRequest.getJson<JsonElement>(provider.versionListURL).toAssetList().toList()
    }
}