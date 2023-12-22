package com.github.purofle.nmsl.auth.microsoft

import com.github.purofle.nmsl.config.Config

suspend fun main() {
    val auth = MicrosoftAuth.authorizationRefreshToken(Config.config.msa.refreshToken)
    Config.createConfig(
        Config.config.copy(
            msa = Config.config.msa.copy(
                accessToken = auth.accessToken,
                refreshToken = auth.refreshToken,
                expiresIn = auth.expiresIn
            )
        )
    )
    println(auth)
}