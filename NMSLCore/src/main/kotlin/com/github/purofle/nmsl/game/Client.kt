package com.github.purofle.nmsl.game


import com.google.gson.annotations.SerializedName

data class Client(
    @SerializedName("sha1")
    val sha1: String, // c0898ec7c6a5a2eaa317770203a1554260699994
    @SerializedName("size")
    val size: Int, // 21462385
    @SerializedName("url")
    val url: String // https://launcher.mojang.com/v1/objects/c0898ec7c6a5a2eaa317770203a1554260699994/client.jar
)