package com.github.purofle.nmsl.auth.microsoft

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.test.Test


class DeviceCodeFlowTest {

    @Test
    fun acquireTokenDeviceCode() {
        runBlocking {
            DeviceCodeFlow().acquireTokenDeviceCode {
                delay(1000)
                println(it)
            }
        }
    }
}