package com.github.purofle.nmsl.game


import com.google.gson.annotations.SerializedName

data class File(
    @SerializedName("id")
    val id: String, // client-1.12.xml
    @SerializedName("sha1")
    val sha1: String, // bd65e7d2e3c237be76cfbef4c2405033d7f91521
    @SerializedName("size")
    val size: Int, // 888
    @SerializedName("url")
    val url: String // https://launcher.mojang.com/v1/objects/bd65e7d2e3c237be76cfbef4c2405033d7f91521/client-1.12.xml
)