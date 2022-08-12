package com.github.purofle.nmsl.game


import com.google.gson.annotations.SerializedName

data class NativesOsx(
    @SerializedName("path")
    val path: String = "", // org/lwjgl/lwjgl/lwjgl-platform/2.9.4-nightly-20150209/lwjgl-platform-2.9.4-nightly-20150209-natives-osx.jar
    @SerializedName("sha1")
    val sha1: String = "", // bcab850f8f487c3f4c4dbabde778bb82bd1a40ed
    @SerializedName("size")
    val size: Int = 0, // 426822
    @SerializedName("url")
    val url: String = "" // https://libraries.minecraft.net/org/lwjgl/lwjgl/lwjgl-platform/2.9.4-nightly-20150209/lwjgl-platform-2.9.4-nightly-20150209-natives-osx.jar
)