package net.versteht.share.database

import kotlinx.datetime.toKotlinLocalDateTime
import net.versteht.share.objects.Group
import net.versteht.share.objects.Appointment
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.datetime


object AppointmentTable: IntIdTable(){
    val item = reference("item", AppointmentTable)
    val startDate = datetime("start")
    val endDate = datetime("end")
}

class AppointmentDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<AppointmentDAO>(AppointmentTable)

    var item by ItemDAO referencedOn AppointmentTable.item
    var startDate by AppointmentTable.startDate
    var endDate by AppointmentTable.endDate
}

fun DAOtoAppointment(dao: AppointmentDAO): Appointment = Appointment(
        DAOtoItem(dao.item),
    dao.startDate.toKotlinLocalDateTime(),
    dao.endDate.toKotlinLocalDateTime()
    )
