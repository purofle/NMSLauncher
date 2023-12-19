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
    @SerialName("refresh_token")
    val refreshToken: String,
    @SerialName("ext_expires_in")
    val extExpiresIn: Int
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
    val error: AuthorizationErrorType,
    @SerialName("error_description")
    val errorDescription: String,
    @SerialName("error_codes")
    val errorCodes: List<Int>,
    @SerialName("timestamp")
    val timestamp: String,
    @SerialName("trace_id")
    val traceId: String,
    @SerialName("correlation_id")
    val correlationId: String,
    @SerialName("error_uri")
    val errorUri: String
)