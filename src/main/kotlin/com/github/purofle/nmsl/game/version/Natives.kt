package com.github.purofle.nmsl.game.version


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Natives(
    val linux: String? = null , // natives-linux
    val osx: String? = null, // natives-macos
    val windows: String? = null// natives-windows
)