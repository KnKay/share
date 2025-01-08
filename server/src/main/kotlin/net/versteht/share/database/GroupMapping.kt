package net.versteht.share.database

import net.versteht.share.objects.Group
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object GroupTable: IntIdTable("group"){
    val name = varchar("name", 50)
}

class GroupDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<GroupDAO>(GroupTable)
    var name by GroupTable.name
    fun toGroup()= Group(name)
}

