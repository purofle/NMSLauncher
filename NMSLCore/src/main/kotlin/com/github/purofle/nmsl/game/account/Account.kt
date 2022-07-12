package com.github.purofle.nmsl.game.account

import com.github.purofle.nmsl.auth.yggdrasil.AvailableProfile
import com.github.purofle.nmsl.auth.yggdrasil.SelectedProfile


data class Account(
    val accessToken: String,
    val clientToken: String,
    val availableProfiles: List<AvailableProfile>,
    val selectedProfile: SelectedProfile,
    val accountTYpe: AccountType
)
