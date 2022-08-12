package com.github.purofle.nmsl.game


import com.google.gson.annotations.SerializedName

data class Natives(
    @SerializedName("linux")
    val linux: String? = null, // natives-linux
    @SerializedName("osx")
    val osx: String? = null, // natives-osx
    @SerializedName("windows")
    val windows: String? = null // natives-windows
)