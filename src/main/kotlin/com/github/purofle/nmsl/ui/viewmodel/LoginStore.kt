package com.github.purofle.nmsl.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.serialization.Serializable

object LoginStore {
    var state: LoginState by mutableStateOf(initialState())
        private set

    private fun initialState() = LoginState(null)
    enum class AccountType {
        Mojang,
        Microsoft,
        Offline
    }
    @Serializable
    data class Account(
        val accountType: AccountType,
        val id: String,
        val name: String,
        val accessToken: String? = null,
        val clientToken: String? = null,
    )
    @Serializable
    data class LoginState(
        val accountList: List<Account>?
    )
}