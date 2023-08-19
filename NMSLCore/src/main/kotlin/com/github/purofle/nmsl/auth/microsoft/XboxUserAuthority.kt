package com.github.purofle.nmsl.auth.microsoft

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class XboxUserAuthority(
    @SerialName("Properties")
    val properties: Properties,
    @SerialName("RelyingParty")
    val relyingParty: String = "http://auth.xboxlive.com",
    @SerialName("TokenType")
    val tokenType: String = "JWT",
)

@Serializable
data class XboxUserAuthorityResponse(
    @SerialName("IssueInstant")
    val issueInstant: String,
    @SerialName("NotAfter")
    val notAfter: String,
    @SerialName("Token")
    val token: String,
    @SerialName("DisplayClaims")
    val displayClaims: DisplayClaims,
)

@Serializable
data class XsTsAuthorizeRequest(
    @SerialName("Properties")
    val properties: PropertiesX,
    @SerialName("RelyingParty")
    val relyingParty: String = "rp://api.minecraftservices.com/",
    @SerialName("TokenType")
    val tokenType: String = "JWT",
)

@Serializable
data class MinecraftAuthenticate(
    @SerialName("identityToken")
    val identityToken: String,
)

@Serializable
data class MinecraftAuthenticateResponse(
    val username: String,
    @SerialName("access_token")
    val accessToken: String
)

@Serializable
data class MinecraftProfile(
    val id: String,
    val name: String,
    val skins: List<Skin>,
    val capes: List<String>,
)

@Serializable
data class Skin(
    val id: String,
    val state: String,
    val url: String,
    val variant: String,
    val alias: String
)

@Serializable
data class PropertiesX(
    @SerialName("SandboxId")
    val sandboxId: String = "RETAIL",
    @SerialName("UserTokens")
    val userTokens: List<String>,
)

@Serializable
data class DisplayClaims(
    @SerialName("xui")
    val xui: List<Xui>,
)

@Serializable
data class Xui(
    val uhs: String,
)

@Serializable
data class Properties(
    @SerialName("AuthMethod")
    val authMethod: String = "RPS",
    @SerialName("SiteName")
    val siteName: String = "user.auth.xboxlive.com",
    @SerialName("RpsTicket")
    val rpsTicket: String,
)
