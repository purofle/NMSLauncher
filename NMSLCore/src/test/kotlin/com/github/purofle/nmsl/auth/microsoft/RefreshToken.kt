package com.github.purofle.nmsl.auth.microsoft

import com.github.purofle.nmsl.config.LauncherConfig

suspend fun main() {
    val auth = MicrosoftAuth.authorizationRefreshToken(LauncherConfig.config.msa.refreshToken)
    LauncherConfig.createConfig(
        LauncherConfig.config.copy(
            msa = LauncherConfig.config.msa.copy(
                accessToken = auth.accessToken,
                refreshToken = auth.refreshToken,
                expiresIn = auth.expiresIn
            )
        )
    )
    println(auth)
}