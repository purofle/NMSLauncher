package com.github.purofle.nmsl.game.version


import kotlinx.serialization.Serializable

@Serializable
data class Classifiers(
    val nativesLinux: Native,
    val nativesMacos: Native,
    val nativesOsx: Native,
    val nativesWindows: Native,
    val sources: Sources
)