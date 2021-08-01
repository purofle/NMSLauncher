package com.github.purofle.nmsl.game.version


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Sources(
    val path: String,
    val size: Int,
    val url: String
)