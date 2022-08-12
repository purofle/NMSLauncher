package com.github.purofle.nmsl.game


import com.google.gson.annotations.SerializedName

data class NativesLinux(
    @SerializedName("path")
    val path: String = "", // org/lwjgl/lwjgl/lwjgl-platform/2.9.4-nightly-20150209/lwjgl-platform-2.9.4-nightly-20150209-natives-linux.jar
    @SerializedName("sha1")
    val sha1: String = "", // 931074f46c795d2f7b30ed6395df5715cfd7675b
    @SerializedName("size")
    val size: Int = 0, // 578680
    @SerializedName("url")
    val url: String = "" // https://libraries.minecraft.net/org/lwjgl/lwjgl/lwjgl-platform/2.9.4-nightly-20150209/lwjgl-platform-2.9.4-nightly-20150209-natives-linux.jar
)