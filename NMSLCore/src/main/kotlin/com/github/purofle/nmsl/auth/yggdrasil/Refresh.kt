package com.github.purofle.nmsl.auth.yggdrasil


data class Refresh(
    val accessToken: String,
    val clientToken: String,
)

data class RefreshResp(
    val accessToken: String,
    val clientToken: String,
    val selectedProfile: SelectedProfile
)