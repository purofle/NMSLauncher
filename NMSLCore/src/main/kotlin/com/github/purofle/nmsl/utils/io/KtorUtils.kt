package com.github.purofle.nmsl.utils.io


import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.util.cio.*
import io.ktor.utils.io.*
import java.io.File

/**
 * 用 ktor 下载文件
 * @param file 文件
 * @param url 链接
 * @param callback 成功后回调
 */
suspend fun HttpClient.downloadFile(file: File, url: String, callback: suspend (boolean: Boolean) -> Unit = {}) {
    val call = request {
        url(url)
        method = HttpMethod.Get
    }
    if (!call.status.isSuccess()) {
        callback(false)
    }
    call.bodyAsChannel().copyAndClose(file.writeChannel())
    return callback(true)
}