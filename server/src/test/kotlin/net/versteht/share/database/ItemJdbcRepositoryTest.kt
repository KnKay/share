package net.versteht.share.database

import kotlinx.coroutines.test.runTest
import net.versteht.share.objects.Group
import net.versteht.share.objects.Item
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class ItemJdbcRepositoryTest {
    internal fun getDut(db: String): ItemJdbcRepository{
        val dut = ItemJdbcRepository(Database.connect("jdbc:h2:mem:${db};DB_CLOSE_DELAY=-1", driver = "org.h2.Driver"))
        transaction {
            SchemaUtils.create(GroupTable)
            SchemaUtils.create(UserTable)
            SchemaUtils.create(CategoryTable)
            SchemaUtils.create(UserGroupsTable)
            SchemaUtils.create(NoteTable)
            CategoryDAO.new {
                name = "testCat"
                open = true
            }
            GroupDAO.new { name = "testing" }
            UserDAO.new {
                username = "tester"
                email = "mymail@test.de"
                firstnames = "my first names"
                lastname = "test"
                confirmation = ""
            }
        }
        return dut
    }

    @Test
    fun create(): Unit = runTest  {
        val dut = getDut("create")
        val insert =transaction {
            val all = ItemDAO.all().count()
            assertEquals(0, all.toInt())
            return@transaction Item(
                name = "myFirstItemn",
                category = DAOtoCategory(CategoryDAO.findById(1)!!),
                owner = DAOtoUser(UserDAO.findById(1)!!),
                delegatedGroup = null,
                notes = null
            )
        }
        dut.create(insert)
        transaction {
            val all = ItemDAO.all().count()
            assertEquals(1, all.toInt())
        }
    }

    @Test
    fun read(): Unit = runTest  {
        val dut = getDut("read")
        val id = transaction {
            return@transaction ItemDAO.new {
                name = "myFirstItem"
                category = CategoryDAO.findById(1)!!
                owner = UserDAO.findById(1)!!
                delegatedGroup = null
            }
        }
        val read = dut.read(1)
        assertEquals("myFirstItem", read?.name)
    }

    @Test
    fun list(): Unit = runTest {
        val dut = getDut("list")
        transaction {
            return@transaction ItemDAO.new {
                name = "myFirstItem"
                category = CategoryDAO.findById(1)!!
                owner = UserDAO.findById(1)!!
                delegatedGroup = null
            }
        }
        assertEquals(1, dut.list().size)
    }

    @Test
    fun delete(): Unit = runTest {
        val dut = getDut("delete")
        val toRemove = transaction {
            return@transaction DAOtoItem( ItemDAO.new {
                name = "myFirstItem"
                category = CategoryDAO.findById(1)!!
                owner = UserDAO.findById(1)!!
                delegatedGroup = null
            })
        }
        dut.delete(toRemove)
        transaction {
            val all = ItemDAO.all().count()
            assertEquals(0, all.toInt())
        }
    }

    @Test
    fun update(): Unit = runTest {
        val dut = getDut("delete")
        var update = transaction {
            return@transaction DAOtoItem( ItemDAO.new {
                name = "myFirstItem"
                category = CategoryDAO.findById(1)!!
                owner = UserDAO.findById(1)!!
                delegatedGroup = null
            })
        }
        update.name = "updated"
        dut.update(update)
        val readBack = dut.read(1)
        assertEquals(update.name, readBack?.name)
    }
}