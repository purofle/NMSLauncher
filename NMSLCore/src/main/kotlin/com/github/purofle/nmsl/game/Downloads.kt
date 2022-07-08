package com.github.purofle.nmsl.game


import com.google.gson.annotations.SerializedName

data class Downloads(
    @SerializedName("client")
    val client: Client,
    @SerializedName("client_mappings")
    val clientMappings: ClientMappings,
    @SerializedName("server")
    val server: Server,
    @SerializedName("server_mappings")
    val serverMappings: ServerMappings
)