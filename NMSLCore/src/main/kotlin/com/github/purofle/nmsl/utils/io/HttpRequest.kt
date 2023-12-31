package com.github.purofle.nmsl.utils.io

import com.github.purofle.nmsl.utils.StringUtils.sha1String
import com.github.purofle.nmsl.utils.io.KtorUtils.DownloadFileBuilder
import com.github.purofle.nmsl.utils.json.JsonUtils
import com.github.purofle.nmsl.utils.json.JsonUtils.toJsonObject
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.utils.io.core.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit
import org.apache.logging.log4j.LogManager
import java.io.File
import kotlin.coroutines.CoroutineContext

object HttpRequest {
    private val logger = LogManager.getLogger("HttpRequest")
    val client = HttpClient(CIO) {
        engine {
            requestTimeout = 0 // 0 to disable
        }
        install(ContentNegotiation) {
            json(JsonUtils.json)
        }
        install(Logging) {
            logger = Logger.SIMPLE
            level = LogLevel.BODY
        }
        install(HttpRequestRetry) {
            retryOnServerErrors(5)
            retryOnExceptionIf(5) { _, cause ->
                cause is ClosedReceiveChannelException
            }
            exponentialDelay()
        }
    }

    /**
     * 使用 [HttpClient.get] 发送请求并且获取相应内容解析后的 json
     * @param url url
     * @return [T]
     */
    suspend inline fun <reified T : Any> getJson(url: String) = client.get(url).bodyAsText().toJsonObject<T>()

    /**
     * 使用 [HttpClient.get] 发送请求并且获取相应内容
     * @param url url
     * @return [String]
     */
    data class DownloadInfo(val url: String, val saveFile: File, val sha1: String? = null)

    /**
     * 下载文件 DSL
     *
     * [DownloadFileBuilder.sha1] 可为 null，为空时不进行 sha1 检查.
     * ```kotlin
     * downloadFile {
     *    url = "https://example.com"
     *    saveFile = File("example")
     *    sha1 = "file sha1"
     *
     *    onProgress { bytesSentTotal, contentLength ->
     *      // Your code
     *    }
     */
    suspend fun downloadFile(init: DownloadFileBuilder.() -> Unit) = withContext(Dispatchers.IO) {
        val download = DownloadFileBuilder().apply(init)

        logger.info("Downloading ${download.url}")

        if (download.saveFile.exists() && download.sha1 != null) {
            if (download.saveFile.sha1String() == download.sha1) {
                logger.debug("File ${download.saveFile.name} already exists, skip download")
                return@withContext
            }
            download.saveFile.delete()
        }

        val request = client.get(download.url)

        download.saveFile.writeBytes(request.readBytes())

        // check sha1
        require(download.sha1 != null && download.saveFile.sha1String() == download.sha1) {
            logger.error("sha1 check failed: ${download.saveFile.name}, sha1: ${download.sha1}, file sha1: ${download.saveFile.sha1String()}")
        }

        logger.info("Downloaded ${download.url}")
    }


    /**
     * 并行下载文件
     * @param files 下载信息
     * @param parallel 并行数
     * @param context 协程上下文
     */
    suspend fun downloadFiles(
        files: List<DownloadInfo>,
        parallel: Int = 64,
        context: CoroutineContext = Dispatchers.IO,
        onDownloadComplete: (file: DownloadInfo) -> Unit = {}
    ) = withContext(context) {

        val semaphore = Semaphore(parallel)

        files.map { downloadInfo ->
            async {
                semaphore.withPermit {
                    downloadFile {
                        downloadInfo(downloadInfo)
                    }
                    onDownloadComplete(downloadInfo)
                }
            }
        }.awaitAll()
    }
}