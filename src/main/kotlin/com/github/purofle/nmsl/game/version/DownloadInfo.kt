package com.github.purofle.nmsl.game.version

import kotlinx.serialization.Serializable

@Serializable
data class DownloadInfo(
    val sha1: String,
    val size: Int,
    val url: String
)
