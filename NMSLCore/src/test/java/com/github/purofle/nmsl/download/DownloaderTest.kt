package com.github.purofle.nmsl.download

import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.system.measureTimeMillis

class DownloaderTest {

    @Test
    fun `test readVersionList time`() {
        val path = "/home/purofle/projects/nmsl/.idea/httpRequests/2022-07-08T183227.200.json"
        val time = measureTimeMillis {
            runBlocking {
                val list = DownloadProvider.readVersionList(path)
                list.collect {
                    println(it)
                }
            }
        }
        println("Collected in $time ms")
    }
}