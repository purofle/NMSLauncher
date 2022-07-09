package com.github.purofle.nmsl.utils.json

import com.github.purofle.nmsl.download.DownloadProvider
import com.github.purofle.nmsl.utils.json.JsonUtils.toAssetList
import com.github.purofle.nmsl.utils.json.JsonUtils.toJsonObject
import com.google.gson.JsonElement
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.io.File
import kotlin.system.measureTimeMillis

class JsonUtilsTest {

    @Test
    fun `test JsonElement toAssetList with flow`() {
        val json = "/home/purofle/projects/nmsl/.idea/httpRequests/2022-07-08T230330.200.json"

        val time = measureTimeMillis {
            runBlocking {
                DownloadProvider.readVersionList(json)
                    .collect()
            }
        }

        println("flow collect on $time ms")
    }

    @Test
    fun `test JsonElement toAssetList without flow`() {
        val json = "/home/purofle/projects/nmsl/.idea/httpRequests/2022-07-08T230330.200.json"

        val time = measureTimeMillis {
            File(json).readText().toJsonObject<JsonElement>().toAssetList()
        }

        println("collect on $time ms")
    }
}