package com.github.purofle.nmsl.game.version


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Natives(
    val linux: String, // natives-linux
    val osx: String, // natives-macos
    val windows: String // natives-windows
)