package com.github.purofle.nmsl.game.version


import kotlinx.serialization.Serializable

@Serializable
data class Os(
    val name: String
)

@Serializable
data class Rule(
    val action: String,
    val os: Os? = null
)

@Serializable
data class Library(
    val downloads: Downloads,
    val name: String, // com.mojang:blocklist:1.0.5
    val natives: Natives? = null,
    val rules: List<Rule>? = null
)