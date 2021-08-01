package com.github.purofle.nmsl.game.version


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DownloadsX(
    val artifact: Artifact,
    val classifiers: Classifiers
)