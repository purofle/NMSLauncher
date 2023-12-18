package com.github.purofle.nmsl.utils.io

import com.github.purofle.nmsl.utils.StringUtils.sha1String
import com.github.purofle.nmsl.utils.json.JsonUtils
import com.github.purofle.nmsl.utils.json.JsonUtils.toJsonObject
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.cache.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit
import kotlinx.coroutines.withContext
import org.apache.logging.log4j.LogManager
import java.io.File
import kotlin.coroutines.CoroutineContext

object HttpRequest {
    private val logger = LogManager.getLogger("HttpRequest")
    val client = HttpClient(CIO) {
        install(HttpCache)
//        install(Logging) {
//            logger = Logger.DEFAULT
//            level = LogLevel.NONE
//        }
        install(ContentNegotiation) {
            json(JsonUtils.json)
        }

        install(HttpRequestRetry) {
            maxRetries = 5
            constantDelay(500)
        }
    }

    /**
     * 使用 [HttpClient.get] 发送请求并且获取相应内容解析后的 json
     * @param url url
     * @return [T]
     */
    suspend inline fun <reified T : Any> getJson(url: String) = client.get(url).bodyAsText().toJsonObject<T>()

    data class DownloadInfo(val url: String, val saveFile: File, val sha1: String? = null)

    suspend fun downloadFile(
        download: DownloadInfo,
        progress: (Long, Long) -> Unit = { _, _ -> }
    ) {
        logger.debug("Downloading ${download.url}")

        if (download.saveFile.exists() && download.sha1 != null) {
            if (download.saveFile.sha1String() == download.sha1) {
                logger.debug("File ${download.saveFile.name} already exists, skip download")
                return
            }
        }

        val request = client.get(download.url) {
            onDownload { bytesSentTotal, contentLength ->
                progress(bytesSentTotal, contentLength)
            }
        }
        val responseBody: ByteArray = request.body()
        download.saveFile.writeBytes(responseBody)
    }


    // Concurrent downloads multiple files
    suspend fun downloadFiles(
        files: List<DownloadInfo>,
        parallel: Int = 16,
        context: CoroutineContext = Dispatchers.IO,
        progress: (String, Long, Long) -> Unit,
    ) {
        withContext(context) {
            val semaphore = Semaphore(parallel)
            files.map {
                async {
                    semaphore.withPermit {

                        downloadFile(it) { bytesSentTotal, contentLength ->
                            progress(it.url, bytesSentTotal, contentLength)
                        }

                        if (it.sha1 == null) return@withPermit
                        require(it.saveFile.sha1String() == it.sha1) {
                            "sha1 check failed: ${it.saveFile.name}, sha1: ${it.sha1}}"
                        }
                    }
                }
            }.awaitAll()
        }
    }
}