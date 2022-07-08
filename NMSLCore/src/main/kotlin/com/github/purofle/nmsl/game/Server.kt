package com.github.purofle.nmsl.game


import com.google.gson.annotations.SerializedName

data class Server(
    @SerializedName("sha1")
    val sha1: String, // e00c4052dac1d59a1188b2aa9d5a87113aaf1122
    @SerializedName("size")
    val size: Int, // 45538778
    @SerializedName("url")
    val url: String // https://launcher.mojang.com/v1/objects/e00c4052dac1d59a1188b2aa9d5a87113aaf1122/server.jar
)