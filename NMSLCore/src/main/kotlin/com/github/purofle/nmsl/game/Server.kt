package com.github.purofle.nmsl.game


import com.google.gson.annotations.SerializedName

data class Server(
    @SerializedName("sha1")
    val sha1: String = "", // 886945bfb2b978778c3a0288fd7fab09d315b25f
    @SerializedName("size")
    val size: Int = 0, // 30222121
    @SerializedName("url")
    val url: String = "" // https://launcher.mojang.com/v1/objects/886945bfb2b978778c3a0288fd7fab09d315b25f/server.jar
)