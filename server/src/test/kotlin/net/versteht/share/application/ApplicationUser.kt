package net.versteht.share.application

import io.ktor.server.config.*
import io.ktor.server.testing.*
import net.versteht.share.database.*
import net.versteht.share.di.database
import net.versteht.share.objects.*
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.Before
import kotlin.test.*



class ApplicationUser {
    var user: User? = null
    var admin: User? = null
    var cat: Category? = null
    var token: String? = null

    @Before
    fun bootstrap(){
        database(config = ApplicationConfig("application-test.conf"))
        transaction {
            SchemaUtils.create(CategoryTable, GroupTable)
            CategoryDAO.new {
                name = "cat1"
                open = false
            }
            cat = DAOtoCategory(CategoryDAO.new {
                name = "cat2"
                open = true
            })
            GroupDAO.new {
                name = "admin"
                open = false
            }
            GroupDAO.new {
                name = "user"
                open = false
            }
            SchemaUtils.create(UserTable, UserGroupsTable)
            CategoryDAO.new {
                user = DAOtoUser( UserDAO.new {
                    username = "tester"
                    email = "mymail@test.de"
                    firstnames = "my first names"
                    lastname = "test"
                    confirmation = ""
                })
                admin = DAOtoUser (UserDAO.new {
                    username = "admin"
                    email = "admin@test.de"
                    firstnames = "my first names"
                    lastname = "test"
                    confirmation = ""
                })
            }
        }

    }

    @Test
    fun userCanCreateItems() = testApplication{
        environment {
            config =ApplicationConfig("application-test.conf")
        }
        val item = Item (
            name = "some stuff to rent",
            owner = user,
            category = cat!!,
            delegatedGroup = null
        )


    }
}
