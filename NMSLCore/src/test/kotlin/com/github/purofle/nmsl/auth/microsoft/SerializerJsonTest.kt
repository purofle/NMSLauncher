package com.github.purofle.nmsl.auth.microsoft

import com.github.purofle.nmsl.download.MCBBSDownloadProvider
import com.github.purofle.nmsl.game.GameJson
import com.github.purofle.nmsl.game.Manifest
import com.github.purofle.nmsl.utils.io.HttpRequest
import com.github.purofle.nmsl.utils.json.JsonUtils
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.decodeFromJsonElement
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.time.measureTime


class SerializerJsonTest {
    private lateinit var manifest: Manifest

    @BeforeTest
    fun beforeTest() = runBlocking {
        val provider = MCBBSDownloadProvider()
        manifest = provider.getManifest().first()
    }

    @Test
    fun serializerTest(): Unit = runBlocking {
        val gameJsonList = manifest.versions.map {
            async {
                println("Downloading ${it.id}")
                it.id to HttpRequest.getJson<JsonElement>(it.url)
            }
        }.awaitAll().toMap()

        gameJsonList.forEach { version ->
            measureTime {
                runCatching {
                    JsonUtils.json.decodeFromJsonElement<GameJson>(version.value).serializerLibrary()
                }.onFailure {
                    println("Error: ${version.key}")
                    throw it
                }
            }.also { println("${version.key}: time: $it") }
        }
    }
}