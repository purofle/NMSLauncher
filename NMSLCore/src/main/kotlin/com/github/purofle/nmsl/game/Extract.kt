package com.github.purofle.nmsl.game


import com.google.gson.annotations.SerializedName

data class Extract(
    @SerializedName("exclude")
    val exclude: List<String> = listOf()
)