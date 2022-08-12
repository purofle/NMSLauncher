package com.github.purofle.nmsl.game


import com.google.gson.annotations.SerializedName

data class Downloads(
    @SerializedName("client")
    val client: Client = Client(),
    @SerializedName("server")
    val server: Server = Server()
)