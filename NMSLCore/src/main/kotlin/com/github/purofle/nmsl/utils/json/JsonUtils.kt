package com.github.purofle.nmsl.utils.json

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object JsonUtils {
    val json = Json {
        encodeDefaults = true
        prettyPrint = true
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
}