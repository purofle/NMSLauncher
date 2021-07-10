package com.github.purofle.nmsl.game.auth.yggdrasil

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class Agent(
    val name: String = "Minecraft",
    val version:Int =  1
)

@Serializable
data class Auth(
    val username: String,
    val password: String,
    val agent: @Contextual Agent.Companion = Agent,
    val requestUser: Boolean = true
)