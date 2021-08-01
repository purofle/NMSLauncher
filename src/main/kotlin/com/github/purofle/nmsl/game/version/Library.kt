package com.github.purofle.nmsl.game.version


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Library(
    val downloads: DownloadsX,
    val name: String, // com.mojang:blocklist:1.0.5
    val natives: Natives,
)