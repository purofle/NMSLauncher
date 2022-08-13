package com.github.purofle.nmsl.utils.json

import com.github.purofle.nmsl.game.Asset
import com.google.gson.Gson
import com.google.gson.JsonObject

object JsonUtils {
    /**
     * 将字符串转换为json对象
     */
    inline fun <reified T : Any> String.toJsonObject(): T {
        return Gson().fromJson(this, T::class.java)
    }

    inline fun <reified T : Any> T.toJsonString(): String {
        return Gson().toJson(this)
    }

    fun assetsToAssets(assets: JsonObject): List<Asset> {
        val assetsList = mutableListOf<Asset>()
        assets["objects"].asJsonObject.entrySet().forEach { entry ->
            assetsList.add(
                Asset(
                    entry.key,
                    entry.value.asJsonObject["hash"].asString,
                    entry.value.asJsonObject["size"].asInt
                )
            )
        }
        return assetsList.toList()
    }
}