package com.github.purofle.nmsl.game

import kotlinx.serialization.Serializable

@Serializable
data class Library(
    val downloads: DownloadsX,
    val extract: Extract?,
    val name: String, // com.mojang:patchy:1.3.9
    val natives: Natives?,
    val rules: List<Rule>?
)

@Serializable
data class Extract(
    val exclude: List<String>
)

@Serializable
data class Natives(
    val linux: String? = null, // natives-linux
    val osx: String? = null, // natives-osx
    val windows: String? = null // natives-windows
)

@Serializable
data class Rule(
    val action: String = "", // allow
    val os: Os?
)

@Serializable
data class Os(
    val name: String = "" // osx
)