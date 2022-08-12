package com.github.purofle.nmsl.game


import com.google.gson.annotations.SerializedName

data class Javadoc(
    @SerializedName("path")
    val path: String = "", // ca/weblite/java-objc-bridge/1.0.0/java-objc-bridge-1.0.0-javadoc.jar
    @SerializedName("sha1")
    val sha1: String = "", // fb0092f22cb4fe8e631452f577b7a238778abf2a
    @SerializedName("size")
    val size: Int = 0, // 174060
    @SerializedName("url")
    val url: String = "" // https://libraries.minecraft.net/ca/weblite/java-objc-bridge/1.0.0/java-objc-bridge-1.0.0-javadoc.jar
)