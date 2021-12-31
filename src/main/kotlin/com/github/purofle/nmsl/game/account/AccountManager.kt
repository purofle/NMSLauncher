package com.github.purofle.nmsl.game.account

import com.github.purofle.nmsl.platforms.Config
import kotlinx.serialization.Serializable

object AccountManager {
    @Serializable
    data class AccountList(val accounts: MutableList<Account>)
    var accountConfig: AccountList by Config()
}