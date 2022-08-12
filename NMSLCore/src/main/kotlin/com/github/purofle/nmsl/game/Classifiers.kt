package com.github.purofle.nmsl.game


import com.google.gson.annotations.SerializedName

data class Classifiers(
    @SerializedName("javadoc")
    val javadoc: Javadoc? = null,
    @SerializedName("natives-linux")
    val nativesLinux: NativesLinux? = null,
    @SerializedName("natives-osx")
    val nativesOsx: NativesOsx? = null,
    @SerializedName("natives-windows")
    val nativesWindows: NativesWindows? = null,
    @SerializedName("sources")
    val sources: Sources? = null
)