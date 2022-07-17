package com.github.purofle.nmsl.game


import com.google.gson.annotations.SerializedName

data class VersionList(
    @SerializedName("latest")
    val latest: Latest,
    @SerializedName("versions")
    val versions: List<Version>
)