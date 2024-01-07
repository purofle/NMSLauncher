package com.github.purofle.nmsl.auth.microsoft

import com.github.purofle.nmsl.config.Config
import com.github.purofle.nmsl.config.LauncherConfig
import com.github.purofle.nmsl.config.Msa
import com.github.purofle.nmsl.config.NmslConfig
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        val deviceFlow = MicrosoftAuth.getDeviceAuthorization()
        println(deviceFlow.message)
        val auth = MicrosoftAuth.authorization(deviceFlow.deviceCode)

//        val auth = DeviceCodeFlow.authorizationRefreshToken(LauncherConfig.config.msa.refreshToken)

        Config.createConfig(
            NmslConfig(
                Msa(
                    accessToken = auth.accessToken,
                    refreshToken = auth.refreshToken,
                    expiresIn = auth.expiresIn
                ),
                MinecraftAuth.authenticate(auth.accessToken),
                LauncherConfig(
                    provider = "mojang"
                )
            )
        )
    }
}