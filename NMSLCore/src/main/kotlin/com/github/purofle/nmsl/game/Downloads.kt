package com.github.purofle.nmsl.game


import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Downloads(
    val client: Client,
    val server: Server
)

@Serializable
data class Client(
    @SerializedName("sha1")
    val sha1: String = "", // 0f275bc1547d01fa5f56ba34bdc87d981ee12daf
    @SerializedName("size")
    val size: Int = 0, // 10180113
    @SerializedName("url")
    val url: String = "" // https://launcher.mojang.com/v1/objects/0f275bc1547d01fa5f56ba34bdc87d981ee12daf/client.jar
)

@Serializable
data class Server(
    val sha1: String, // 886945bfb2b978778c3a0288fd7fab09d315b25f
    val size: Int, // 30222121
    val url: String // https://launcher.mojang.com/v1/objects/886945bfb2b978778c3a0288fd7fab09d315b25f/server.jar
)