package com.github.purofle.nmsl.game


import com.google.gson.annotations.SerializedName

data class NativesWindows(
    @SerializedName("path")
    val path: String = "", // org/lwjgl/lwjgl/lwjgl-platform/2.9.4-nightly-20150209/lwjgl-platform-2.9.4-nightly-20150209-natives-windows.jar
    @SerializedName("sha1")
    val sha1: String = "", // b84d5102b9dbfabfeb5e43c7e2828d98a7fc80e0
    @SerializedName("size")
    val size: Int = 0, // 613748
    @SerializedName("url")
    val url: String = "" // https://libraries.minecraft.net/org/lwjgl/lwjgl/lwjgl-platform/2.9.4-nightly-20150209/lwjgl-platform-2.9.4-nightly-20150209-natives-windows.jar
)