package net.versteht.share.client

import kotlinx.coroutines.*
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.*
import io.ktor.util.Hash
import net.versteht.share.objects.Login

// Our share client should encapsulate our application data...
class ShareClient(client: HttpClient) {
    val client = client


    companion object Factory{
        suspend fun createInstance(baseUrl: String, email: String, password: String, engine: HttpClientEngine): ShareClient  {
            val httpClient = HttpClient(engine) {
                install(ContentNegotiation) {
                    json()
                }
            }

            httpClient.post("${baseUrl}/login"){
                header(
                    HttpHeaders.ContentType,
                    ContentType.Application.Json
                )
                setBody(Login(email = email, password = password))
            }

            return ShareClient(httpClient)

        }
    }
}