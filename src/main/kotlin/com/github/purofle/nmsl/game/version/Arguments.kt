package com.github.purofle.nmsl.game.version


import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Arguments(
    val game: List<@Contextual Any>,
    val jvm: List<@Contextual Any>
)