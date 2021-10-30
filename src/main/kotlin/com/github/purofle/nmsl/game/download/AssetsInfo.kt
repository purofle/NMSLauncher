package com.github.purofle.nmsl.game.download

import kotlinx.serialization.Serializable

@Serializable
data class AssetInfo(val hash: String, val size: Int)
@Serializable
data class Assets(val objects: HashMap<String, AssetInfo>)
