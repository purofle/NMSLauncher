package com.github.purofle.nmsl.game


import com.google.gson.annotations.SerializedName

data class ServerMappings(
    @SerializedName("sha1")
    val sha1: String, // 1c1cea17d5cd63d68356df2ef31e724dd09f8c26
    @SerializedName("size")
    val size: Int, // 5552032
    @SerializedName("url")
    val url: String // https://launcher.mojang.com/v1/objects/1c1cea17d5cd63d68356df2ef31e724dd09f8c26/server.txt
)