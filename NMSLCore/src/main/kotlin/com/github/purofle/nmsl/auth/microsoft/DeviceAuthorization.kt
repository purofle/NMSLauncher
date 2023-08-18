package com.github.purofle.nmsl.auth.microsoft

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DeviceAuthorization(
    @SerialName("device_code")
    val deviceCode: String,
    @SerialName("user_code")
    val userCode: String,
    @SerialName("verification_uri")
    val verificationUri: String,
    @SerialName("expires_in")
    val expiresIn: Int,
    @SerialName("interval")
    val interval: Int,
    @SerialName("message")
    val message: String
)

@Serializable
data class SuccessAuthentication(
    @SerialName("token_type")
    val tokenType: String,
    @SerialName("scope")
    val scope: String,
    @SerialName("expires_in")
    val expiresIn: Int,
    @SerialName("access_token")
    val accessToken: String,
)

enum class AuthorizationErrorType {
    @SerialName("authorization_pending")
    PENDING,

    @SerialName("authorization_declined")
    DECLINED,

    @SerialName("bad_verification_code")
    BAD_VERIFICATION_CODE,

    @SerialName("expired_token")
    EXPIRED_TOKEN,
}

@Serializable
data class AuthorizationError(
    val error: AuthorizationErrorType
)