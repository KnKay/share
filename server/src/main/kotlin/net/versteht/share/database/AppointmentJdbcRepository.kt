package net.versteht.share.database



import kotlinx.datetime.toKotlinLocalDateTime
import net.versteht.share.objects.Appointment
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.deleteWhere
import java.time.LocalDateTime
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class  AppointmentJdbcRepository(database: Database) : CrudRepositoryInterface<Appointment> {

    init {
        transaction(database) {
            addLogger(StdOutSqlLogger)
            SchemaUtils.create(AppointmentTable)
        }
    }

    override suspend fun create(t: Appointment): Appointment?  = suspendTransaction {
        DAOtoAppointment(AppointmentDAO.new{
            item = ItemDAO.findById(t.item.id!!)!!
            requester = UserDAO.findById(t.requester?.id!!)!!
            startDate = LocalDateTime.parse(t.startDate)
            endDate = LocalDateTime.parse(t.endDate)
            confirmed = false
        })
    }

    override suspend fun read(id: Int): Appointment?  = suspendTransaction {
        AppointmentDAO
            .find { (AppointmentTable.id eq id )}
            .limit(1)
            .map(::DAOtoAppointment)
            .firstOrNull()
    }

    override suspend fun list(): List<Appointment> = suspendTransaction {
        AppointmentDAO
                .all()
                .map(::DAOtoAppointment)
    }

    override suspend fun delete(t: Appointment): Boolean = suspendTransaction {
        1 == AppointmentTable.deleteWhere { id eq t.id }
    }

    override suspend fun update(t: Appointment): Appointment = suspendTransaction {
        var ret = AppointmentDAO.findByIdAndUpdate(t.id!!){
            it.confirmed = t.confirmed
        }
        DAOtoAppointment(ret!!)
    }

}
