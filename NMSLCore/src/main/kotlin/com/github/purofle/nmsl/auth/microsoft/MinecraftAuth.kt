package com.github.purofle.nmsl.auth.microsoft

import com.github.purofle.nmsl.utils.io.HttpRequest.client
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

object MinecraftAuth {
    suspend fun authenticate(accessToken: String): MinecraftProfile {

        val userAuthenticate: XboxUserAuthorityResponse = client.post(AUTHORITY) {
            contentType(ContentType.Application.Json)
            setBody(XboxUserAuthority(Properties(rpsTicket = "d=$accessToken")))
        }.body()

        val xstsAuthenticate: XboxUserAuthorityResponse = client.post(AUTHORITY_XSTS) {
            contentType(ContentType.Application.Json)
            setBody(XsTsAuthorizeRequest(PropertiesX(userTokens = listOf(userAuthenticate.token))))
        }.body()

        val xstsToken = xstsAuthenticate.token
        val minecraftAuth: MinecraftAuthenticateResponse = client.post(MINECRAFT_AUTHENTICATION) {
            contentType(ContentType.Application.Json)
            setBody(MinecraftAuthenticate("XBL3.0 x=${xstsAuthenticate.displayClaims.xui[0].uhs};$xstsToken"))
        }.body()

        // checking game ownership
        val profile: MinecraftProfile = client.get(MINECRAFT_MC_PROFILE) {
            headers {
                append("Authorization", "Bearer ${minecraftAuth.accessToken}")
            }
        }.body()

        return profile
    }

    private const val AUTHORITY = "https://user.auth.xboxlive.com/user/authenticate"
    private const val AUTHORITY_XSTS = "https://xsts.auth.xboxlive.com/xsts/authorize"
    private const val MINECRAFT_AUTHENTICATION = "https://api.minecraftservices.com/authentication/login_with_xbox"
    private const val MINECRAFT_MC_PROFILE = "https://api.minecraftservices.com/minecraft/profile"
}