package com.github.purofle.nmsl.game


import kotlinx.serialization.Serializable

@Serializable
data class JavaVersion(
    val component: String, // jre-legacy
    val majorVersion: Int // 8
)