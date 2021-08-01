package com.github.purofle.nmsl.game.version


import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class Arguments(
    val game: List<JsonElement>,
    val jvm: List<JsonElement>
)