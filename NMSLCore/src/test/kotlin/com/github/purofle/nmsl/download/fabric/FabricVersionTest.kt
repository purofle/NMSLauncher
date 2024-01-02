package com.github.purofle.nmsl.download.fabric

import kotlinx.coroutines.runBlocking
import kotlin.test.Test

class FabricVersionTest {
    @Test
    fun getFabricVersionTest() = runBlocking {
        val fabricVersion = FabricVersion.getFabricVersion()
        println(fabricVersion)
    }
}