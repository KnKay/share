package net.versteht.share.client

import io.ktor.http.HttpHeaders
import kotlin.test.Test
import kotlinx.coroutines.test.runTest
import io.ktor.client.engine.mock.*
import io.ktor.http.*
import io.ktor.utils.io.*
import kotlinx.coroutines.*

class ClientTest {
    @Test
    fun testFactory() = runTest {
        val mockEngine = MockEngine { request ->
            respond(
                content = ByteReadChannel("""hh""".trimIndent()),
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }
        val dut = ShareClient.Factory.createInstance("test", "","", mockEngine)
    }
}