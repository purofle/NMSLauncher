package com.github.purofle.nmsl.game


import com.google.gson.annotations.SerializedName

data class Logging(
    @SerializedName("client")
    val client: ClientX = ClientX()
)