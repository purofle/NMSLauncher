package com.github.purofle.nmsl.auth.microsoft

import com.github.purofle.nmsl.utils.io.HttpRequest.client
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*


class DeviceCodeFlow {
    suspend fun acquireTokenDeviceCode(
        callback: suspend (String) -> Unit
    ) {
        client.post(AUTHORITY) {
            contentType(ContentType.Application.FormUrlEncoded)
            setBody(FormDataContent(Parameters.build {
                append("client_id", CLIENT_ID)
                append("scope", SCOPE.joinToString(" "))
            }))
        }
    }

    companion object {
        const val CLIENT_ID = "8eaa9578-7a05-48c6-8aeb-41211fd20b31"
        const val AUTHORITY = "https://login.microsoftonline.com/consumers"
        val SCOPE = setOf("XboxLive.signin")
    }
}