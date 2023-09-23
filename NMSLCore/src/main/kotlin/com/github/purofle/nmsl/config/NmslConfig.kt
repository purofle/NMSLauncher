package com.github.purofle.nmsl.config

import com.github.purofle.nmsl.auth.microsoft.MinecraftProfile
import kotlinx.serialization.Serializable

@Serializable
data class NmslConfig(
    val accessToken: String,
    val refreshToken: String,
    val profile: MinecraftProfile
)