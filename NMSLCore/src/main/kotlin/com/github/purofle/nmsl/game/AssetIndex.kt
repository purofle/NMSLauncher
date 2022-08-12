package com.github.purofle.nmsl.game


import com.google.gson.annotations.SerializedName

data class AssetIndex(
    @SerializedName("id")
    val id: String = "", // 1.12
    @SerializedName("sha1")
    val sha1: String = "", // 1584b57c1a0b5e593fad1f5b8f78536ca640547b
    @SerializedName("size")
    val size: Int = 0, // 143138
    @SerializedName("totalSize")
    val totalSize: Int = 0, // 129336389
    @SerializedName("url")
    val url: String = "" // https://launchermeta.mojang.com/v1/packages/1584b57c1a0b5e593fad1f5b8f78536ca640547b/1.12.json
)