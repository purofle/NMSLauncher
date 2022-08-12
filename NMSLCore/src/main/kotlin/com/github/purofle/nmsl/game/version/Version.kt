package com.github.purofle.nmsl.game.version


import com.google.gson.annotations.SerializedName

data class Version(
    @SerializedName("complianceLevel")
    val complianceLevel: Int = 0, // 1
    @SerializedName("id")
    val id: String = "", // 1.19.2
    @SerializedName("releaseTime")
    val releaseTime: String = "", // 2022-08-05T11:57:05+00:00
    @SerializedName("sha1")
    val sha1: String = "", // 68cded4616fba9fbefb3f895033c261126c5f89c
    @SerializedName("time")
    val time: String = "", // 2022-08-05T12:01:02+00:00
    @SerializedName("type")
    val type: String = "", // release
    @SerializedName("url")
    val url: String = "" // https://piston-meta.mojang.com/v1/packages/68cded4616fba9fbefb3f895033c261126c5f89c/1.19.2.json
)