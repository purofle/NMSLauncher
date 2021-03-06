package com.github.purofle.nmsl.auth.yggdrasil

data class Agent(
    val name: String = "Minecraft",
    val version:Int =  1
)

data class Auth(
    val username: String,
    val password: String,
    val agent: Agent = Agent(),
)

data class AccessToken(
    val iss: String,
    val sub: String,
    val yggt: String,
    val exp: Long,
    val iat: Long,
    val spr: String
)

data class Validate(
    val accessToken: String
)