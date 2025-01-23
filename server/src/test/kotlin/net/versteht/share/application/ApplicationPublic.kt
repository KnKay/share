package net.versteht.share.application

//import org.junit.Before
import io.ktor.server.testing.*
import net.versteht.share.module

import kotlin.test.*

import io.ktor.client.*
import com.jayway.jsonpath.DocumentContext
import com.jayway.jsonpath.JsonPath
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.config.*
import org.jetbrains.exposed.sql.transactions.transaction

//https://ktor.io/docs/server-create-restful-apis.html#unit-tests-via-jsonpath
class ApplicationPublic {

    internal fun populate(){

    }

    @Test
    fun listCategories() = testApplication{
        environment {
            config = ApplicationConfig("application-test.conf")
        }
        val jsonDoc = client.getAsJsonPath("/categories")
        val result: List<String> = jsonDoc.read("$[*].name")
    }

    suspend fun HttpClient.getAsJsonPath(url: String): DocumentContext {
        val response = this.get(url) {
            accept(ContentType.Application.Json)
        }
        return JsonPath.parse(response.bodyAsText())
    }
}