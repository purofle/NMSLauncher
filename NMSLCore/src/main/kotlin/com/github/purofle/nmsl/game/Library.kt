package com.github.purofle.nmsl.game

import com.google.gson.annotations.SerializedName

data class Library(
    @SerializedName("downloads")
    val downloads: DownloadsX = DownloadsX(),
    @SerializedName("extract")
    val extract: Extract? = Extract(),
    @SerializedName("name")
    val name: String = "", // com.mojang:patchy:1.3.9
    @SerializedName("natives")
    val natives: Natives? = Natives(),
    @SerializedName("rules")
    val rules: List<Rule>? = listOf()
)