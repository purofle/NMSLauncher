package com.github.purofle.nmsl.game.auth.yggdrasil

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import java.nio.charset.Charset
import java.util.*

class Login(
    private val username: String,
    private val password: String,
    logLevel: LogLevel
) {
    private val baseUrl = "https://authserver.mojang.com"
    private val client = HttpClient(CIO) {
        install(JsonFeature) {
            serializer = KotlinxSerializer()
        }
        install(Logging) {
            logger = Logger.DEFAULT
            level = logLevel
        }
    }
    private suspend inline fun <reified T> request(endpoint: String, requestBody: Any?): T {
        return client.post(baseUrl+endpoint) {
            contentType(ContentType.Application.Json)
            if (requestBody != null) {
                body = requestBody
            }
        }
    }

    suspend fun authenticate(): Boolean {
        val resp = request<MojangAccount>("/authenticate", Auth(username, password))
        val accessToken = Base64.getDecoder().decode(resp.accessToken.split(".")[1]).toString()
        print(accessToken)
        val refresh = Refresh(accessToken, resp.clientToken)
        request<Refresh>("/refresh", refresh)
        return when (request<HttpResponse>("/validate", """{"accessToken": ${accessToken}}""").status.value) {
            204 -> true
            else -> false
        }
    }
}