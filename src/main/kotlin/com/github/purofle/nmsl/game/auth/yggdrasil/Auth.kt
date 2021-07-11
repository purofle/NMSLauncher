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
    val agent: Agent = Agent(),
)

@Serializable
data class AccessToken(
    val iss: String,
    val sub: String,
    val yggt: String,
    val exp: Long,
    val iat: Long,
    val spr: String
)

@Serializable
data class Validate(
    val accessToken: String
)