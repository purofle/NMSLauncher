package com.github.purofle.nmsl.game.version


import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class Downloads(
    val artifact: Artifact,
    val classifiers: Classifiers? = null
)