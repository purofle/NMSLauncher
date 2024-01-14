package com.github.purofle.nmsl.download.fabric

import kotlinx.coroutines.runBlocking
import kotlin.test.Test

class FabricVersionTest {
    @Test
    fun getFabricVersionTest() = runBlocking {
        val fabricVersion = FabricVersion.getFabricVersion()
        val fabricMeta = getFabricMeta(fabricVersion.first().metaUrl)
        println(fabricMeta.launcherMeta.libraries.common.first())
    }
}