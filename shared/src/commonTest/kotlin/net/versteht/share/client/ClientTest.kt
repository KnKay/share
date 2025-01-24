package net.versteht.share.client

import io.ktor.client.engine.cio.CIO
import kotlin.test.Test

class ClientTest {
    @Test
    fun testFactory(){
        val mockEngine = MockEngine { request ->
            respond(
                content = ByteReadChannel("""{"ip":"127.0.0.1"}"""),
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }
        val dut = ShareClient.Factory.createInstance("test", "","", CIO.create())
    }
}