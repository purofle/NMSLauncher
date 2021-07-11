package com.github.purofle.nmsl.game.auth.yggdrasil

import kotlinx.serialization.Serializable


@Serializable
data class Refresh(
    val accessToken: String,
    val clientToken: String,
)

@Serializable
data class RefreshResp(
    val accessToken: String,
    val clientToken: String,
    val selectedProfile: SelectedProfile
)