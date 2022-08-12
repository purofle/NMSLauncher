package com.github.purofle.nmsl.game


import com.google.gson.annotations.SerializedName

data class Artifact(
    @SerializedName("path")
    val path: String = "", // com/mojang/patchy/1.3.9/patchy-1.3.9.jar
    @SerializedName("sha1")
    val sha1: String = "", // eb8bb7b66fa0e2152b1b40b3856e82f7619439ee
    @SerializedName("size")
    val size: Int = 0, // 23581
    @SerializedName("url")
    val url: String = "" // https://libraries.minecraft.net/com/mojang/patchy/1.3.9/patchy-1.3.9.jar
)