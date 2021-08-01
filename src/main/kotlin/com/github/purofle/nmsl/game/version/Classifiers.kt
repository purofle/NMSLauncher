package com.github.purofle.nmsl.game.version


import kotlinx.serialization.Serializable

@Serializable
data class Classifiers(
    val nativesLinux: Native? = null,
    val nativesMacos: Native? = null,
    val nativesOsx: Native? = null,
    val nativesWindows: Native? = null,
    val sources: Sources? = null
)