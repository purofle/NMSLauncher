package com.github.purofle.nmsl.game


import com.google.gson.annotations.SerializedName

data class ClientX(
    @SerializedName("argument")
    val argument: String, // -Dlog4j.configurationFile=${path}
    @SerializedName("file")
    val `file`: File,
    @SerializedName("type")
    val type: String // log4j2-xml
)