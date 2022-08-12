package com.github.purofle.nmsl.game


import com.google.gson.annotations.SerializedName

data class Client(
    @SerializedName("sha1")
    val sha1: String = "", // 0f275bc1547d01fa5f56ba34bdc87d981ee12daf
    @SerializedName("size")
    val size: Int = 0, // 10180113
    @SerializedName("url")
    val url: String = "" // https://launcher.mojang.com/v1/objects/0f275bc1547d01fa5f56ba34bdc87d981ee12daf/client.jar
)