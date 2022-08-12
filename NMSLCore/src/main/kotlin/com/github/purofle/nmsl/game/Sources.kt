package com.github.purofle.nmsl.game


import com.google.gson.annotations.SerializedName

data class Sources(
    @SerializedName("path")
    val path: String = "", // com/mojang/text2speech/1.10.3/text2speech-1.10.3-sources.jar
    @SerializedName("sha1")
    val sha1: String = "", // 404339fe43d1011ee046a249b0ec7ae9ce04a834
    @SerializedName("size")
    val size: Int = 0, // 4632
    @SerializedName("url")
    val url: String = "" // https://libraries.minecraft.net/com/mojang/text2speech/1.10.3/text2speech-1.10.3-sources.jar
)