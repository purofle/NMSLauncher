package com.github.purofle.nmsl.game


import com.google.gson.annotations.SerializedName

data class Artifact(
    @SerializedName("path")
    val path: String, // com/mojang/logging/1.0.0/logging-1.0.0.jar
    @SerializedName("sha1")
    val sha1: String, // f6ca3b2eee0b80b384e8ed93d368faecb82dfb9b
    @SerializedName("size")
    val size: Int, // 15343
    @SerializedName("url")
    val url: String // https://libraries.minecraft.net/com/mojang/logging/1.0.0/logging-1.0.0.jar
)