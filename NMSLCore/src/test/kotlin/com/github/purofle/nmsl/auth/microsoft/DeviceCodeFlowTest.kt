package com.github.purofle.nmsl.auth.microsoft

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        val deviceFlow = DeviceCodeFlow.getDeviceAuthorization()
        println(deviceFlow)
        val accessToken = DeviceCodeFlow.authorizationFlow(deviceFlow.deviceCode).first().accessToken
        println(accessToken)
        println(MinecraftAuth.authenticate(accessToken))
    }
}