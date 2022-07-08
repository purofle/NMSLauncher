package com.github.purofle.nmsl.utils.json

import com.github.purofle.nmsl.game.Asset
import com.github.purofle.nmsl.game.AssetInfo
import com.google.gson.Gson
import com.google.gson.JsonElement

object JsonUtils {
    /**
     * 将字符串转换为json对象
     */
    inline fun <reified T : Any> String.toJsonObject(): T {
        return Gson().fromJson(this, T::class.java)
    }

    fun JsonElement.toAssetList(): MutableList<Asset> {
        val allObject = this.asJsonObject.get("objects").asJsonObject
        val assetList = mutableListOf<Asset>()

        allObject.entrySet().forEach {
            assetList.add(Asset(it.key, Gson().fromJson(it.value, AssetInfo::class.java)))
        }

        return assetList
    }
}