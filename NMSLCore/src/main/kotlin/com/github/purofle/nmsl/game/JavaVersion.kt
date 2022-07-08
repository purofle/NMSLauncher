package com.github.purofle.nmsl.game


import com.google.gson.annotations.SerializedName

data class JavaVersion(
    @SerializedName("component")
    val component: String, // java-runtime-gamma
    @SerializedName("majorVersion")
    val majorVersion: Int // 17
)