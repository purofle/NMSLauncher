package com.github.purofle.nmsl.utils.io

import com.github.purofle.nmsl.utils.json.JsonUtils.toJsonObject
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.cache.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

object HttpRequest {
    val client = HttpClient(CIO) {
        install(HttpCache)
        install(Logging)
    }

    /**
     * 使用 [HttpClient.get] 发送请求并且获取相应内容解析后的 json
     * @param url url
     * @return [T]
     */
    suspend inline fun <reified T : Any> getJson(url: String) = client.get(url).bodyAsText().toJsonObject<T>()
}