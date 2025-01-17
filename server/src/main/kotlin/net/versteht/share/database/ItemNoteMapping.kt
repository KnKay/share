package net.versteht.share.database

import net.versteht.share.objects.ItemNote
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object ItemNoteTable: IntIdTable(){
    val note = varchar("note", 160)
    val item = reference("item", ItemTable)
}

class ItemNoteDAO(id: EntityID<Int>): IntEntity(id) {
    companion object: IntEntityClass<ItemNoteDAO>(ItemNoteTable)
    var note by ItemNoteTable.note
    var item by ItemDAO referencedOn ItemNoteTable.item
    fun toItemNote() =  ItemNote(DAOtoItem(item), note)
}

