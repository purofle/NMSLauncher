package com.github.purofle.nmsl.utils.json

import com.google.gson.Gson

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
}