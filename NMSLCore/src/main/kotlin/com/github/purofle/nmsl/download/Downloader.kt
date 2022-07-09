package com.github.purofle.nmsl.download

import com.github.purofle.nmsl.game.Asset
import com.github.purofle.nmsl.utils.io.HttpRequest
import com.github.purofle.nmsl.utils.json.JsonUtils.toAssetList
import com.google.gson.JsonElement

class Downloader(private val provider: DownloadProvider) {
    private suspend fun getVersionList(): List<Asset> {
        return HttpRequest.getJson<JsonElement>(provider.versionListURL).toAssetList().toList()
    }
}