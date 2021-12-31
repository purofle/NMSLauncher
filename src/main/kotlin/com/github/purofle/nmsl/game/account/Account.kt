package com.github.purofle.nmsl.game.account

import com.github.purofle.nmsl.game.auth.yggdrasil.AvailableProfile
import com.github.purofle.nmsl.game.auth.yggdrasil.SelectedProfile
import kotlinx.serialization.Serializable

@Serializable
data class Account(
    val accessToken: String,
    val clientToken: String,
    val availableProfiles: List<AvailableProfile>,
    val selectedProfile: SelectedProfile,
    val accountTYpe: AccountType
)
