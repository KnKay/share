package net.versteht.share.database

import net.versteht.share.objects.Category
import net.versteht.share.objects.Note
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object NoteTable: IntIdTable(){
    val note = varchar("note", 150)
    val item = reference("item", ItemTable)
}

class NoteDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<NoteDAO>(NoteTable)
    var note by NoteTable.note
    var item by ItemDAO referencedOn  NoteTable.item
}

fun DAOtoNote(dao: NoteDAO): Note = Note(dao.note, DAOtoItem(dao.item))

