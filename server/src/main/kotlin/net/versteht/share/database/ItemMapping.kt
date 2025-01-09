package net.versteht.share.database

import net.versteht.share.objects.Group
import net.versteht.share.objects.Item
import net.versteht.share.objects.Note
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object ItemTable: IntIdTable(){
    val name = varchar("name", 50)
    val category = reference("category", CategoryTable)
    val owner = reference("owner", UserTable)
    val delegatedGroup = reference("delegatedGroup", GroupTable).nullable()
}

class ItemDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<ItemDAO>(ItemTable)
    var name by ItemTable.name
    var category by CategoryDAO referencedOn ItemTable.category
    var owner by UserDAO referencedOn ItemTable.owner
    var delegatedGroup by GroupDAO optionalReferencedOn ItemTable.delegatedGroup

}

fun DAOtoItem(dao: ItemDAO): Item{
    var delegation: Group? = null
    if (dao.delegatedGroup != null){
        val test = dao.delegatedGroup
        delegation = DAOtoGroup(test!!)
    }
    return Item(
        dao.name,
        DAOtoCategory(dao.category),
        DAOtoUser(dao.owner),
        delegation,
        null
    )
}