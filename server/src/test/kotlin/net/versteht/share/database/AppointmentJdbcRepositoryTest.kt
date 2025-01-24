package net.versteht.share.database

import kotlinx.coroutines.test.runTest
import kotlinx.datetime.DateTimeUnit

import net.versteht.share.objects.Appointment
import net.versteht.share.objects.Category
import net.versteht.share.objects.Item
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.test.Test
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.time.Duration

class AppointmentJdbcRepositoryTest {

    internal fun getDut(db: String): AppointmentJdbcRepository{
        val dut = AppointmentJdbcRepository(Database.connect("jdbc:h2:mem:${db};DB_CLOSE_DELAY=-1", driver = "org.h2.Driver"))
        transaction {
            SchemaUtils.create(GroupTable)
            SchemaUtils.create(UserTable)
            SchemaUtils.create(CategoryTable)
            SchemaUtils.create(UserGroupsTable)
            SchemaUtils.create(ItemNoteTable)
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
            ItemDAO.new{
                name = "myFirstItemn"
                category = CategoryDAO.findById(1)!!
                owner = UserDAO.findById(1)!!
                delegatedGroup = null
            }
        }
        return dut
    }

    @Test
    fun create(): Unit = runTest {
        val dut = getDut("create")
        val toCreate = transaction {
            val timeZone = TimeZone.of("Europe/Berlin")
            val instant = LocalDateTime(2021, 3, 27, 2, 16, 20).toInstant(timeZone)
            val tomorrow = LocalDateTime(2021, 3, 28, 2, 16, 20).toInstant(timeZone)
            return@transaction Appointment(
                DAOtoItem(ItemDAO.findById(1)!!),
                instant.toLocalDateTime(timeZone).toString(),
                tomorrow.toLocalDateTime(timeZone).toString(),
                false,
                DAOtoUser(UserDAO.findById(1)!!),
                null
            )
        }
        dut.create(toCreate)
        transaction {
            assertEquals(1, AppointmentDAO.all().count().toInt())
        }
    }

    @Test
    fun read(): Unit = runTest {
        val dut = getDut("read")
        val toRead = transaction {
            AppointmentDAO.new {
                item = ItemDAO.findById(1)!!
                requester = UserDAO.findById(1)!!
                startDate = java.time.LocalDateTime.now()
                endDate = java.time.LocalDateTime.now()
                confirmed = false
            }
        }
        var ret = dut.read(1)
        assertEquals(false, ret?.confirmed)
    }


    @Test
    fun list(): Unit = runTest {
        val dut = getDut("list")
        transaction {
            AppointmentDAO.new {
                item = ItemDAO.findById(1)!!
                requester = UserDAO.findById(1)!!
                startDate = java.time.LocalDateTime.now()
                endDate = java.time.LocalDateTime.now()
                confirmed = false
            }
        }
        assertEquals(dut.list().size, 1)
    }

    @Test
    fun delete(): Unit = runTest {
        val dut = getDut("delete")
        var ret = transaction {
            return@transaction DAOtoAppointment(AppointmentDAO.new {
                item = ItemDAO.findById(1)!!
                requester = UserDAO.findById(1)!!
                startDate = java.time.LocalDateTime.now()
                endDate = java.time.LocalDateTime.now()
                confirmed = false
            })
        }
        dut.delete(ret)
        transaction{
            assertEquals(0, AppointmentDAO.all().count().toInt())
        }
    }

    @Test
    fun update(): Unit = runTest {
        val dut = getDut("update")
        var ret = transaction {
            var new = AppointmentDAO.new {
                item = ItemDAO.findById(1)!!
                requester = UserDAO.findById(1)!!
                startDate = java.time.LocalDateTime.now()
                endDate = java.time.LocalDateTime.now()
                confirmed = false
            }
            return@transaction DAOtoAppointment(new)
        }
        ret.confirmed = true
        dut.update(ret)
        transaction{
            assertEquals(true, AppointmentDAO.findById(1)?.confirmed)
        }
    }
}