package com.github.purofle.nmsl.game


import com.google.gson.annotations.SerializedName

data class Latest(
    @SerializedName("release")
    val release: String, // 1.19
    @SerializedName("snapshot")
    val snapshot: String // 1.19.1-pre5
)