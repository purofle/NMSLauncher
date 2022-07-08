package com.github.purofle.nmsl.game


import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

data class Arguments(
    @SerializedName("game")
    val game: List<JsonElement>,
    @SerializedName("jvm")
    val jvm: List<JsonElement>
)