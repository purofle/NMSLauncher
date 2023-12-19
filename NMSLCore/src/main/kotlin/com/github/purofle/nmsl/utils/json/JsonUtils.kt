package com.github.purofle.nmsl.utils.json

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.decodeFromJsonElement

object JsonUtils {
    val json = Json {
        encodeDefaults = true
        prettyPrint = true
        ignoreUnknownKeys = true
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

    inline fun <reified T : Any> JsonElement.decode(): T {
        return json.decodeFromJsonElement(this)
    }
}