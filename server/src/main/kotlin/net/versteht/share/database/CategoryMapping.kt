package net.versteht.share.database

import net.versteht.share.objects.Category
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object CategoryTable: IntIdTable(){
    val name = varchar("name", 50)
    val open = bool("open")
}

class CategoryDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<CategoryDAO>(CategoryTable)
    var name by CategoryTable.name
    var open by CategoryTable.open
}

fun DAOtoCategory(dao: CategoryDAO): Category = Category(dao.name, dao.open, dao.id.value)
