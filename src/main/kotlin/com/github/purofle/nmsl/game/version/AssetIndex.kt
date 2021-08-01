package com.github.purofle.nmsl.game.version


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AssetIndex(
    val id: String, // 1.17
    val sha1: String, // e5af543d9b3ce1c063a97842c38e50e29f961f00
    val size: Int, // 345623
    val totalSize: Int, // 345028703
    val url: String // https://launchermeta.mojang.com/v1/packages/e5af543d9b3ce1c063a97842c38e50e29f961f00/1.17.json
)