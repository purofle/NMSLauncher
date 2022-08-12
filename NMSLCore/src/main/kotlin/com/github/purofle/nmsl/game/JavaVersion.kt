package com.github.purofle.nmsl.game


import com.google.gson.annotations.SerializedName

data class JavaVersion(
    @SerializedName("component")
    val component: String = "", // jre-legacy
    @SerializedName("majorVersion")
    val majorVersion: Int = 0 // 8
)