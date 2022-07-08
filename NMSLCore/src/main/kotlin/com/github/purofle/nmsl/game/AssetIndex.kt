package com.github.purofle.nmsl.game


import com.google.gson.annotations.SerializedName

data class AssetIndex(
    @SerializedName("id")
    val id: String, // 1.19
    @SerializedName("sha1")
    val sha1: String, // 298a5e9205e475561c78b5ead43cb94de9ffa751
    @SerializedName("size")
    val size: Int, // 385416
    @SerializedName("totalSize")
    val totalSize: Int, // 554720575
    @SerializedName("url")
    val url: String // https://piston-meta.mojang.com/v1/packages/298a5e9205e475561c78b5ead43cb94de9ffa751/1.19.json
)