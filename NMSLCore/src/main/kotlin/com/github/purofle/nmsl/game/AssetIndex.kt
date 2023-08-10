package com.github.purofle.nmsl.game


import kotlinx.serialization.Serializable

@Serializable
data class AssetIndex(
    val id: String = "", // 1.12
    val sha1: String = "", // 1584b57c1a0b5e593fad1f5b8f78536ca640547b
    val size: Int = 0, // 143138
    val totalSize: Int = 0, // 129336389
    val url: String = "" // https://launchermeta.mojang.com/v1/packages/1584b57c1a0b5e593fad1f5b8f78536ca640547b/1.12.json
)