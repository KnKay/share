package net.versteht.share.client.repositories

import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.MockEngine.Companion.invoke
import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.utils.io.ByteReadChannel
import kotlinx.coroutines.test.runTest
import net.versteht.share.client.ShareClient
import kotlin.test.Test
import kotlin.test.assertEquals

class CategoryRepositoryTest {
    @Test
    fun testList() = runTest {
        val mockEngine = MockEngine { request ->
            respond(
                content = ByteReadChannel("""    [
    {
        "name": "tools",
        "open": false,
        "id": 1
    },
    {
        "name": "knowledge",
        "open": false,
        "id": 2
    }
    ]
    """.trimIndent()),
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }
        val dut = ShareClient.Factory.createInstance("test", "","", mockEngine)
        val ret = dut.categoryRepository.list()
        assertEquals(2, ret.size)
    }

}