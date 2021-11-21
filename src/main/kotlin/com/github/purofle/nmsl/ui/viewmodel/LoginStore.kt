package com.github.purofle.nmsl.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.github.purofle.nmsl.game.auth.yggdrasil.MojangAccount
import kotlinx.serialization.Serializable

internal class LoginStore {
    var state: LoginState by mutableStateOf(initialState())
        private set

    private fun initialState() = LoginState(null)


    @Serializable
    data class LoginState(
        val mojangAccount: MojangAccount?
    )
}