package net.versteht.share.database

import kotlinx.coroutines.test.runTest
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.test.Test
import kotlin.test.assertEquals


class ItemNoteJdbcRepositoryTest {
    internal fun getDut(db: String): ItemNoteJdbcRepository{
        val dut = ItemNoteJdbcRepository(Database.connect("jdbc:h2:mem:${db};DB_CLOSE_DELAY=-1", driver = "org.h2.Driver"))
        transaction {
            SchemaUtils.create(ItemTable)
            SchemaUtils.create(GroupTable)
            SchemaUtils.create(UserTable)
            SchemaUtils.create(CategoryTable)
            SchemaUtils.create(UserGroupsTable)
            SchemaUtils.create(ItemNoteTable)
            val grp = GroupDAO.new { name = "testing" }
            val use = UserDAO.new {
                username = "tester"
                email = "mymail@test.de"
                firstnames = "my first names"
                lastname = "test"
                confirmation = ""
            }
            val cat = CategoryDAO.new {
                name = "testCat"
                open = true
            }
            ItemDAO.new {
                category = CategoryDAO.findById(1)!!
                owner = UserDAO.findById(1)!!
                name = "myFirstItem"
                delegatedGroup = null
            }
        }
        return dut
    }
    @Test
    fun create() {
    }

    @Test
    fun read(): Unit = runTest  {
        val dut = getDut("read")
        transaction {
            ItemNoteDAO.new {
                note = "this is a note"
                item = ItemDAO.findById(1)!!
            }
        }
        val read = dut.read(1)
        assertEquals("this is a note", read?.note)
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