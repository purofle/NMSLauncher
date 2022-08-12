package com.github.purofle.nmsl.game.version


import com.google.gson.annotations.SerializedName

data class Versions(
    @SerializedName("latest")
    val latest: Latest = Latest(),
    @SerializedName("versions")
    val versions: List<Version> = listOf()
)