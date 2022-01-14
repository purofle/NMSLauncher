package com.github.purofle.nmsl.game.account

import com.github.purofle.nmsl.game.auth.yggdrasil.AvailableProfile
import com.github.purofle.nmsl.game.auth.yggdrasil.SelectedProfile
import org.junit.Test

class AccountManagerTest {

    @Test
    fun `Write account`() {
        val al = mutableListOf<Account>()
        for (i in 0..5) {
            al.add(Account(
                "ac",
                "ct",
                listOf(AvailableProfile("", "")),
                SelectedProfile("", ""),
                AccountType.Mojang
            ))
        }
        AccountManager.accountConfig = AccountManager.AccountList(al)
        check(AccountManager.accountConfig == AccountManager.AccountList(al))
    }
}