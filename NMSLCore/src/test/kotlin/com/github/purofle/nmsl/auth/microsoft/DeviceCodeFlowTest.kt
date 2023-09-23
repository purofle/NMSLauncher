package com.github.purofle.nmsl.auth.microsoft

import com.github.purofle.nmsl.config.LauncherConfig
import com.github.purofle.nmsl.config.NmslConfig
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        val deviceFlow = DeviceCodeFlow.getDeviceAuthorization()
        println(deviceFlow)
        val accessToken = DeviceCodeFlow.authorizationRefreshToken("想偷我的 refreshToken?")
//        val accessToken = DeviceCodeFlow.authorizationFlow(deviceFlow.deviceCode).first()

        LauncherConfig.createConfig(
            NmslConfig(
                accessToken.accessToken,
                accessToken.refreshToken,
                MinecraftAuth.authenticate(accessToken.accessToken)
            )
        )
    }
}