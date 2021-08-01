package com.github.purofle.nmsl.game.version

import kotlinx.serialization.Serializable

@Serializable
data class Native(
    val path: String,
    val sha1: String,
    val size: Int,
    val url: String
)
