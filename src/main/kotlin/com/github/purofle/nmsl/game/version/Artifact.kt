package com.github.purofle.nmsl.game.version


import kotlinx.serialization.Serializable

@Serializable
data class Artifact(
    val path: String, // com/mojang/blocklist/1.0.5/blocklist-1.0.5.jar
    val sha1: String, // 9da540f21c9a8d5ed7c029e1f88d1a6dabb0d6ad
    val size: Int, // 964
    val url: String // https://libraries.minecraft.net/com/mojang/blocklist/1.0.5/blocklist-1.0.5.jar
)