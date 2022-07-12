package com.github.purofle.nmsl.auth.yggdrasil


data class AvailableProfile(
    val id: String,
    val name: String
)

data class SelectedProfile(
    val id: String,
    val name: String
)

data class MojangAccount(
    val accessToken: String,
    val clientToken: String,
    val availableProfiles: List<AvailableProfile>,
    val selectedProfile: SelectedProfile
)