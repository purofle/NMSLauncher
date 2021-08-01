package com.github.purofle.nmsl.game.version


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Sources(
    @SerialName("path")
    val path: String, // org/lwjgl/lwjgl/3.2.1/lwjgl-3.2.1-sources.jar
    @SerialName("sha1")
    val sha1: String, // 106f90ac41449004a969309488aa6e3a2f7d6731
    @SerialName("size")
    val size: Int, // 255671
    @SerialName("url")
    val url: String // https://libraries.minecraft.net/org/lwjgl/lwjgl/3.2.1/lwjgl-3.2.1-sources.jar
)