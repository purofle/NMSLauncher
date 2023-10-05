package com.github.purofle.nmsl.config

import com.github.purofle.nmsl.auth.microsoft.AuthData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NmslConfig(
    val msa: Msa,
//    val xbox: Xbox,
    val profile: AuthData,
)

@Serializable
data class Xbox(
    val xuid: String
)

@Serializable
data class Msa(
    val accessToken: String,
    val refreshToken: String,
    @SerialName("expires_in")
    val expiresIn: Int,
)