package com.github.purofle.nmsl.game

data class Version(
    val id: String,
    val releaseTime: String,
    val time: String,
    val type: String,
    val url: String
)

data class Latest(
    val release: String,
    val snapshot: String
)

data class McVersionManifest(
    val latest: Latest,
    val versions: List<Version>
)