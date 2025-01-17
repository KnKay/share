package net.versteht.share.database

import kotlinx.coroutines.test.runTest
import net.versteht.share.objects.Category
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.test.Test
import org.junit.jupiter.api.Assertions.*

class CategoryJdbcRepositoryTest {

    internal fun getDut(db: String): CategoryJdbcRepository{
        val dut = CategoryJdbcRepository(Database.connect("jdbc:h2:mem:${db};DB_CLOSE_DELAY=-1", driver = "org.h2.Driver"))
        return dut
    }

    @Test
    fun create(): Unit = runTest {
        val dut = getDut("create")
        transaction {
            val all = CategoryDAO.all().count()
            assertEquals(0, all.toInt())
        }
        val insert = Category("created", false)
        dut.create(insert)
        transaction {
            val all = CategoryDAO.all().count()
            assertEquals(1, all.toInt())
        }
    }

    @Test
    fun read(): Unit = runTest {
        val dut = getDut("read")
        val toCreate = Category("to_read", true)
        val id = transaction {
            return@transaction CategoryDAO.new {
                name = toCreate.name
                open = toCreate.open
            }
        }
         val read = dut.read(1)
        assertEquals(toCreate.name, read?.name)
    }

    @Test
    fun list(): Unit = runTest {
        val dut = getDut("list")
        transaction {
            CategoryDAO.new {
                name = "eins"
                open = false
            }
            CategoryDAO.new {
                name = "zwei"
                open = true
            }
        }
        val ret = dut.list()
        assertEquals(2, ret.size)
    }

    @Test
    fun delete(): Unit = runTest {
        val dut = getDut("delete")
        val toRemove = transaction {
            CategoryDAO.new {
                name = "eins"
                open = false
            }
            return@transaction CategoryDAO.findById(1)
        }
        dut.delete(DAOtoCategory(toRemove!!))
        transaction {
            val all = CategoryDAO.all().count()
            assertEquals(0, all.toInt())
        }
    }

    @Test
    fun update(): Unit = runTest {
        val dut = getDut("update")
        transaction {
            CategoryDAO.new {
                name = "eins"
                open = false
            }
        }
        val toUpdate = Category("einsUpdate", true, 1)
        dut.update(toUpdate)
        val readBack = dut.read(1)
        assertEquals(toUpdate.name, readBack?.name)
    }
}