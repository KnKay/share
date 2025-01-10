package net.versteht.share.database

import kotlinx.coroutines.test.runTest
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.Assertions.*
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.text.insert

class GroupJdbcRepositoryTest {

    @Test
    fun list(): Unit = runTest {
        val dut = GroupJdbcRepository(Database.connect("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", driver = "org.h2.Driver"))
        transaction {
            addLogger(StdOutSqlLogger)
            SchemaUtils.create(GroupTable)
            GroupTable.insert {
                it[name] = "admin"
            }
            GroupTable.insert {
                it[name] = "user"
            }
        }
        val data = dut.list()
        assertEquals(2, data.size)
    }
}