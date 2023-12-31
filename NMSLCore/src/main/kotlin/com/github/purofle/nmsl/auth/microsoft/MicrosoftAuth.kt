package com.github.purofle.nmsl.auth.microsoft

import com.github.purofle.nmsl.auth.microsoft.AuthorizationErrorType.PENDING
import com.github.purofle.nmsl.utils.io.HttpRequest.client
import com.github.purofle.nmsl.utils.json.JsonUtils.toJsonObject
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.concurrent.CancellationException


object MicrosoftAuth {
    suspend fun getDeviceAuthorization(): DeviceAuthorization {
        return client.post("${AUTHORITY}/oauth2/v2.0/devicecode") {
            contentType(ContentType.Application.FormUrlEncoded)
            setBody("client_id=$CLIENT_ID&scope=${SCOPE.joinToString("%20")}")
        }.body()
    }

    private suspend fun authorizationDeviceCode(deviceCode: String): String {
        return client.post("${AUTHORITY}/oauth2/v2.0/token") {
            contentType(ContentType.Application.FormUrlEncoded)
            setBody(
                FormDataContent(
                    Parameters.build {
                        append("grant_type", "urn:ietf:params:oauth:grant-type:device_code")
                        append("client_id", CLIENT_ID)
                        append("device_code", deviceCode)
                        append("scope", SCOPE.joinToString("%20"))
                    })
            )
        }.body()
    }

    suspend fun authorizationRefreshToken(refreshToken: String, scope: String? = null): SuccessAuthentication {
        return client.post("${AUTHORITY}/oauth2/v2.0/token") {
            contentType(ContentType.Application.FormUrlEncoded)
            setBody(
                FormDataContent(
                    Parameters.build {
                        append("grant_type", "refresh_token")
                        append("client_id", CLIENT_ID)
                        append("refresh_token", refreshToken)
                        append("scope", scope ?: SCOPE.joinToString(" "))
                    })
            )
        }.body()
    }

    fun authorizationFlow(deviceCode: String): Flow<SuccessAuthentication> = flow {
        var getToken = true
        while (getToken) {
            val result = authorizationDeviceCode(deviceCode)
            runCatching {
                val authError = result.toJsonObject<AuthorizationError>()
                if (authError.error != PENDING) {
                    throw Exception(authError.error.toString())
                }
            }.onFailure {
                if (it is CancellationException) {
                    throw it
                }
                emit(result.toJsonObject<SuccessAuthentication>())
                getToken = false
            }.onSuccess {
                delay(5000)
            }
        }
    }

    const val CLIENT_ID = "8eaa9578-7a05-48c6-8aeb-41211fd20b31"
    private const val AUTHORITY = "https://login.microsoftonline.com/consumers"
    private val SCOPE = setOf("XboxLive.signin", "offline_access")
}