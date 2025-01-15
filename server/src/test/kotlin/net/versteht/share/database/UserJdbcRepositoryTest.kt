package net.versteht.share.database

import kotlinx.coroutines.test.runTest
import net.versteht.share.objects.User
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction


import org.junit.jupiter.api.Assertions.*
import kotlin.test.Test

class UserJdbcRepositoryTest {

    internal fun getDut(db: String): UserJdbcRepository{
        val dut = UserJdbcRepository(Database.connect("jdbc:h2:mem:${db};DB_CLOSE_DELAY=-1", driver = "org.h2.Driver"))
        return dut
    }

    @Test
    fun register(): Unit = runTest {
        val dut = getDut("register")
        val user = User("registered","test@registration.de", "geheim", "Hans Werner", "Tester", null)
        dut.register(user)
        val entry = transaction { return@transaction  UserDAO.find(UserTable.email eq user.email).firstOrNull() }
        assertEquals(entry?.lastname, user.lastname)
        assertNotNull(entry?.confirmation)
    }

    @Test
    fun confirm(): Unit = runTest {
        val dut = getDut("confirm")
        val user = User("registered","confirm@registration.de", "geheim", "Hans Werner", "Tester", null)
        dut.register(user)
        val registered = transaction {
            val found =  UserDAO.find{UserTable.confirmation neq ""}
            assertEquals(1,found.count())
            return@transaction found.firstOrNull()
        }
        dut.confirm(registered?.confirmation!!)
        transaction {
            val found =  UserDAO.find{UserTable.confirmation neq ""}
            assertEquals(0,found.count())

        }
    }

    @Test
    fun create() {
    }

    @Test
    fun read() {
    }

    @Test
    fun list() {
    }

    @Test
    fun delete() {
    }

    @Test
    fun update() {
    }

}