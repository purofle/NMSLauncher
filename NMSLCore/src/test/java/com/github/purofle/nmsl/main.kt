package com.github.purofle.nmsl

import com.github.purofle.nmsl.download.DownloadProvider
import com.google.gson.Gson
import com.google.gson.JsonElement
import kotlinx.coroutines.runBlocking
import java.io.File
import kotlin.system.measureTimeMillis

fun main() {
    val path = "/home/purofle/projects/nmsl/.idea/httpRequests/2022-07-08T230330.200.json"
    Gson().fromJson(File(path).readText(), JsonElement::class.java)
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