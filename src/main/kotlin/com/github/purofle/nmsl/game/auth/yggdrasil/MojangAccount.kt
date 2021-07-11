package com.github.purofle.nmsl.game.auth.yggdrasil

import kotlinx.serialization.Serializable

@Serializable
data class AvailableProfile(
    val id: String,
    val name: String
)

@Serializable
data class SelectedProfile(
    val id: String,
    val name: String
)

@Serializable
data class MojangAccount(
    val accessToken: String,
    val clientToken: String,
    val availableProfiles: List<AvailableProfile>,
    val selectedProfile: SelectedProfile
)