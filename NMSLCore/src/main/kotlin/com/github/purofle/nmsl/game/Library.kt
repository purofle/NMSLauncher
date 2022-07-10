package com.github.purofle.nmsl.game


import com.google.gson.annotations.SerializedName


data class Library(
    @SerializedName("downloads")
    val downloads: DownloadsX,
    @SerializedName("name")
    val name: String, // com.mojang:logging:1.0.0
    @SerializedName("rules")
    val rules: List<Rule>?
)