package com.github.purofle.nmsl.utils

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object JsonUtils {
    val format = Json {
        encodeDefaults = true
        ignoreUnknownKeys = true
    }

    inline fun <reified T: Any> T.toJsonString(): String {
        return format.encodeToString(this)
    }
}