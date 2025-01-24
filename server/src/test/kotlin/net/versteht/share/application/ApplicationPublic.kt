package net.versteht.share.application

//import org.junit.Before
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.testing.*
import net.versteht.share.module
import io.ktor.client.call.*

import io.ktor.client.request.*
import kotlin.test.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.testing.*
import kotlin.test.*

import io.ktor.server.config.*
import net.versteht.share.database.*
import net.versteht.share.di.database
import net.versteht.share.objects.Item

import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.Before

//https://ktor.io/docs/server-create-restful-apis.html#unit-tests-via-jsonpath
class ApplicationPublic {

    @Before
    fun bootstrap(){
        database(config = ApplicationConfig("application-test.conf"))
        transaction {
            SchemaUtils.create(CategoryTable, GroupTable)
            CategoryDAO.new {
                name = "cat1"
                open = false
            }
            CategoryDAO.new {
                name = "cat2"
                open = true
            }
            GroupDAO.new {
                name = "admin"
                open = false
            }
            GroupDAO.new {
                name = "user"
                open = false
            }

        }
    }


    @Test
    fun allCanListCategories() = testApplication{

        environment {
            config =ApplicationConfig("application-test.conf")
        }

        val jsonDoc = client.getAsJsonPath("/categories")
        val result: List<String> = jsonDoc.read("$[*].name")
        assertEquals(2, result.size)
        assertEquals("cat1", result[0])
    }

    @Test
    fun allCanListGroups() = testApplication {
        environment {
            config =ApplicationConfig("application-test.conf")
        }

        val jsonDoc = client.getAsJsonPath("/groups")
        val result: List<String> = jsonDoc.read("$[*].name")
        assertEquals(2, result.size)
        assertEquals("admin", result[0])
    }

    @Test
    fun publicCannotAddGroups() = testApplication {
        environment {
            config =ApplicationConfig("application-test.conf")
        }
        val category = transaction {
            return@transaction CategoryDAO.findById(1)
        }
        val user = transaction {
            return@transaction CategoryDAO.findById(1)
        }
        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }
        val item = Item(
            "test_item",
            category = DAOtoCategory(category!!),
            owner = null,
            delegatedGroup = null
            )
        val response = client.post("/items") {
            header(
                HttpHeaders.ContentType,
                ContentType.Application.Json
            )

            setBody(item)
        }
        assertEquals(HttpStatusCode.Unauthorized, response.status)
    }


}