package com.github.purofle.nmsl.game.version

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.File

fun versionTest(json: String) {
    val format = Json { ignoreUnknownKeys = true }
    val version = format.decodeFromString<Version>(json)
    version.libraries.forEach {
        println(it.name)
    }
}

fun main() {
    versionTest(File("/home/purofle/Project/NMSL-Launcher/src/test/kotlin/com/github/purofle/nmsl/vesion_1-17-1.json").readText())
}