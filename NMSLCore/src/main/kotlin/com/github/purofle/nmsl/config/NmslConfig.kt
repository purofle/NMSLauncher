package com.github.purofle.nmsl.config

import com.github.purofle.nmsl.auth.microsoft.MinecraftProfile
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NmslConfig(
    val msa: Msa,
    val profile: MinecraftProfile
)

@Serializable
data class Msa(
    val accessToken: String,
    val refreshToken: String,
    @SerialName("expires_in")
    val expiresIn: Int,
)