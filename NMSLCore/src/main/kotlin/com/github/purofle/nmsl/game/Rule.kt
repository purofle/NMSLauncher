package com.github.purofle.nmsl.game


import com.google.gson.annotations.SerializedName

data class Rule(
    @SerializedName("action")
    val action: String = "", // allow
    @SerializedName("os")
    val os: Os? = null
)