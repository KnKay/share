package net.versteht.share.application

import com.jayway.jsonpath.DocumentContext
import com.jayway.jsonpath.JsonPath
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*


suspend fun HttpClient.getAsJsonPath(url: String): DocumentContext {
    val response = this.get(url) {
        accept(ContentType.Application.Json)
    }
    return JsonPath.parse(response.bodyAsText())
}

fun HttpClient.auth(user: User){

}