package com.github.purofle.nmsl.game.version


import kotlinx.serialization.Serializable

@Serializable
data class Versions(
    val latest: Latest,
    val versions: List<Version>
)

@Serializable
data class Latest(
    val release: String, // 1.19.2
    val snapshot: String // 1.19.2
)

@Serializable
data class Version(
    val complianceLevel: Int, // 1
    val id: String, // 1.19.2
    val releaseTime: String, // 2022-08-05T11:57:05+00:00
    val sha1: String, // 68cded4616fba9fbefb3f895033c261126c5f89c
    val time: String, // 2022-08-05T12:01:02+00:00
    val type: String, // release
    val url: String // https://piston-meta.mojang.com/v1/packages/68cded4616fba9fbefb3f895033c261126c5f89c/1.19.2.json
)