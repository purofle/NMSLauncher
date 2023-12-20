package com.github.purofle.nmsl.utils.io

import java.io.File

object KtorUtils {
    /**
     * Download file builder
     * @property url String
     * @property saveFile File
     * @property sha1 String?
     */
    class DownloadFileBuilder {
        var url: String = ""
        var saveFile: File = File("")
        var sha1: String? = null
        var onProgress: ((bytesSentTotal: Long, contentLength: Long) -> Unit)? = null

        fun onProgress(onProgress: (Long, Long) -> Unit) =
            apply { this.onProgress = onProgress }

        fun downloadInfo(info: HttpRequest.DownloadInfo) = apply {
            url = info.url
            saveFile = info.saveFile
            sha1 = info.sha1
        }
    }
}