package com.github.purofle.nmsl.game


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Download(
    val sha1: String,
    val size: Int,
    val url: String,
    val path: String? = null
)

@Serializable
data class Downloads(
    val client: Download,
    val server: Download,
    @SerialName("client_mappings") val clientMappings: Download,
    @SerialName("server_mappings") val serverMappings: Download
)