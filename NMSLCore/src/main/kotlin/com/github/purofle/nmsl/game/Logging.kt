package com.github.purofle.nmsl.game


import kotlinx.serialization.Serializable

@Serializable
data class Logging(
    val client: ClientLogging
)

@Serializable
data class ClientLogging(
    val argument: String, // -Dlog4j.configurationFile=${path}
    val file: ClientFile,
    val type: String = "" // log4j2-xml
)

@Serializable
data class ClientFile(
    val id: String, // client-1.12.xml
    val sha1: String, // bd65e7d2e3c237be76cfbef4c2405033d7f91521
    val size: Int, // 888
    val url: String // https://launcher.mojang.com/v1/objects/bd65e7d2e3c237be76cfbef4c2405033d7f91521/client-1.12.xml
)