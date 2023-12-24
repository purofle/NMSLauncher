package com.github.purofle.nmsl.config

import com.github.purofle.nmsl.auth.microsoft.AuthData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NmslConfig(
    val msa: Msa = Msa(),
//    val xbox: Xbox,
    val profile: AuthData = AuthData(),
    val launcherConfig: LauncherConfig = LauncherConfig()
)

@Serializable
data class Msa(
    val accessToken: String = "",
    val refreshToken: String = "",
    @SerialName("expires_in")
    val expiresIn: Int = 0,
)

@Serializable
data class LauncherConfig(
    val provider: String = "mcbbs",
)