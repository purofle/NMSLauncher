package com.github.purofle.nmsl.game.auth.yggdrasil

import kotlinx.serialization.Serializable

@Serializable
data class MojangAccount(
    val accessToken: String,
    val clientToken: String
)