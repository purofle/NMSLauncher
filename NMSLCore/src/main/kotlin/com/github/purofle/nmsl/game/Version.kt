package com.github.purofle.nmsl.game


import com.google.gson.annotations.SerializedName

data class Version(
    @SerializedName("complianceLevel")
    val complianceLevel: Int, // 1
    @SerializedName("id")
    val id: String, // 1.19.1-pre5
    @SerializedName("releaseTime")
    val releaseTime: String, // 2022-07-15T11:51:44+00:00
    @SerializedName("sha1")
    val sha1: String, // 3e53dbf54f22f38d132c9328b12e8e604d2e8a28
    @SerializedName("time")
    val time: String, // 2022-07-15T11:56:41+00:00
    @SerializedName("type")
    val type: String, // snapshot
    @SerializedName("url")
    val url: String // https://piston-meta.mojang.com/v1/packages/3e53dbf54f22f38d132c9328b12e8e604d2e8a28/1.19.1-pre5.json
)