package net.versteht.share.database

import kotlinx.coroutines.test.runTest
import net.versteht.share.objects.Category
import net.versteht.share.objects.Group
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.Assertions.*
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.text.insert

class GroupJdbcRepositoryTest {
    internal fun getDut(db: String): GroupJdbcRepository{
        val dut = GroupJdbcRepository(Database.connect("jdbc:h2:mem:${db};DB_CLOSE_DELAY=-1", driver = "org.h2.Driver"))
        return dut
    }

    @Test
    fun create(): Unit = runTest {
        val dut = getDut("create")
        transaction {
            val all = GroupDAO.all().count()
            assertEquals(0, all.toInt())
        }
        val insert = Group("one")
        dut.create(insert)
        transaction {
            val all = GroupDAO.all().count()
            assertEquals(1, all.toInt())
        }
    }

    @Test
    fun read(): Unit = runTest {
        val dut = getDut("read")
        val toCreate = Group("created")
        val id = transaction {
            return@transaction GroupDAO.new {
                name = toCreate.name
            }
        }
        val read = dut.read(1)
        assertEquals(toCreate.name, read?.name)
    }

    @Test
    fun list(): Unit = runTest {
        val dut = getDut("list")
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

    @Test
    fun delete(): Unit = runTest {
        val dut = getDut("delete")
        val toRemove = transaction {
            GroupDAO.new {
                name = "eins"
            }
            return@transaction GroupDAO.findById(1)
        }
        dut.delete(DAOtoGroup(toRemove!!))
        transaction {
            val all = CategoryDAO.all().count()
            assertEquals(0, all.toInt())
        }
    }

    @Test
    fun update(): Unit = runTest {
        val dut = getDut("update")
        transaction {
            GroupDAO.new {
                name = "eins"
            }
        }
        val toUpdate = Group("einsUpdate", 1)
        dut.update(toUpdate)
        val readBack = dut.read(1)
        assertEquals(toUpdate.name, readBack?.name)
    }
}