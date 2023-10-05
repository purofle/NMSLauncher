package com.github.purofle.nmsl.auth.microsoft

import com.github.purofle.nmsl.config.LauncherConfig
import com.github.purofle.nmsl.config.Msa
import com.github.purofle.nmsl.config.NmslConfig
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
//        val deviceFlow = DeviceCodeFlow.getDeviceAuthorization()
//        println(deviceFlow)
//        val auth = DeviceCodeFlow.authorizationFlow(deviceFlow.deviceCode).first()

        val auth = DeviceCodeFlow.authorizationRefreshToken(LauncherConfig.config.msa.refreshToken)

        LauncherConfig.createConfig(
            NmslConfig(
                Msa(
                    accessToken = auth.accessToken,
                    refreshToken = auth.refreshToken,
                    expiresIn = auth.expiresIn
                ),
                MinecraftAuth.authenticate(auth.accessToken)
            )
        )
    }
}