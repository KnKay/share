package net.versteht.share.database



import io.ktor.server.routing.*
import net.versteht.share.objects.Appointment
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.StdOutSqlLogger

class  AppointmentJdbcRepository(database: Database) : CrudRepositoryInterface<Appointment> {

    init {
        transaction(database) {
            addLogger(StdOutSqlLogger)
            SchemaUtils.create(AppointmentTable)
        }
    }

    override suspend fun create(t: Appointment): Boolean {
        TODO("Not yet implemented")
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

    override suspend fun delete(t: Appointment): Appointment {
        TODO("Not yet implemented")
    }

    override suspend fun update(t: Appointment): Appointment {
        TODO("Not yet implemented")
    }

}
