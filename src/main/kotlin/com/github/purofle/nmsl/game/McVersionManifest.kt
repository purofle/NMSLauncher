package com.github.purofle.nmsl.game

import kotlinx.serialization.Serializable

@Serializable
data class Version(
    val id: String,
    val releaseTime: String,
    val time: String,
    val type: String,
    val url: String
)

@Serializable
data class Latest(
    val release: String,
    val snapshot: String
)

@Serializable
data class McVersionManifest(
    val latest: Latest,
    val versions: List<Version>,
)