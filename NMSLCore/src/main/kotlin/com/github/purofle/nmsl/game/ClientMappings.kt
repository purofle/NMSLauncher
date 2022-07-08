package com.github.purofle.nmsl.game


import com.google.gson.annotations.SerializedName

data class ClientMappings(
    @SerializedName("sha1")
    val sha1: String, // 150346d1c0b4acec0b4eb7f58b86e3ea1aa730f3
    @SerializedName("size")
    val size: Int, // 7123832
    @SerializedName("url")
    val url: String // https://launcher.mojang.com/v1/objects/150346d1c0b4acec0b4eb7f58b86e3ea1aa730f3/client.txt
)