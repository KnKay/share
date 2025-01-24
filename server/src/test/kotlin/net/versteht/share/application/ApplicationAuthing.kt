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
import net.versteht.share.objects.Login

import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.Before

//https://ktor.io/docs/server-create-restful-apis.html#unit-tests-via-jsonpath
class ApplicationAuthing {

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
            SchemaUtils.create(UserTable, UserGroupsTable)
            DAOtoUser( UserDAO.new {
                username = "tester"
                email = "mymail@test.de"
                firstnames = "my first names"
                lastname = "test"
                confirmation = ""
            })
            DAOtoUser (UserDAO.new {
                username = "admin"
                email = "admin@test.de"
                firstnames = "my first names"
                lastname = "test"
                confirmation = ""
            })
        }
    }


    @Test
    fun allCanLogin() = testApplication{
        environment {
            config =ApplicationConfig("application-test.conf")
        }
        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }
        val login = Login(email = "admin@test.de", password = "secret")
        val response = client.post("/login") {
            header(
                HttpHeaders.ContentType,
                ContentType.Application.Json
            )
            setBody(login)
        }
        assertEquals(HttpStatusCode.OK, response.status)
    }

}