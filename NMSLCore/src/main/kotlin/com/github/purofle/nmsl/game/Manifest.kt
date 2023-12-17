package com.github.purofle.nmsl.game

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class VersionType {
    @SerialName("release")
    RELEASE,

    @SerialName("snapshot")
    SNAPSHOT,

    @SerialName("old_beta")
    OLD_BETA,

    @SerialName("old_alpha")
    OLD_ALPHA
}

@Serializable
data class Latest(
    val release: String,
    val snapshot: String
)

@Serializable
data class Version(
    val id: String,
    val type: VersionType,
    val url: String,
    val time: String,
    val releaseTime: String,
    val sha1: String,
    val complianceLevel: Int
)

@Serializable
data class Manifest(
    val latest: Latest,
    val versions: List<Version>
)
