package com.github.purofle.nmsl.auth.microsoft

import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        val deviceFlow = DeviceCodeFlow.getDeviceAuthorization()
        println(deviceFlow)
        DeviceCodeFlow.authorizationFlow(deviceFlow.deviceCode).collect(::println)
    }
}