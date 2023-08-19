package com.github.purofle.nmsl.utils.json

import com.github.purofle.nmsl.game.Asset
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.*

object JsonUtils {
    val json = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
    }
    /**
     * 将字符串转换为json对象
     */
    inline fun <reified T : Any> String.toJsonObject(): T {
        return json.decodeFromString(this)
    }

    inline fun <reified T : Any> T.toJsonString(): String {
        return json.encodeToString(this)
    }

    fun assetsToAssets(assets: JsonObject): List<Asset> {
        val assetsList = mutableListOf<Asset>()
        assets["objects"]?.jsonObject?.forEach { entry ->
            assetsList.add(
                Asset(
                    entry.key,
                    entry.value.jsonObject["hash"]?.jsonPrimitive?.content!!,
                    entry.value.jsonObject["size"]?.jsonPrimitive?.int!!
                )
            )
        }
        return assetsList.toList()
    }
}