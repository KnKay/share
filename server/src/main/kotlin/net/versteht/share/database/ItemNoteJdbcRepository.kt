package net.versteht.share.database



import net.versteht.share.objects.ItemNote
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class  ItemNoteJdbcRepository(database: Database) : CrudRepositoryInterface<ItemNote> {

    init {
        transaction(database) {
            addLogger(StdOutSqlLogger)
            SchemaUtils.create(ItemNoteTable)
        }
    }

    override suspend fun create(t: ItemNote): ItemNote = suspendTransaction  {
        var item = ItemDAO.findById(t.item.id!!)
        ItemNoteDAO.new {
            item = item!!
            note = t.note
        }.toItemNote()
    }

    override suspend fun read(id: Int): ItemNote?  = suspendTransaction {
        ItemNoteDAO.findById(id)?.toItemNote()
    }

    override suspend fun list(): List<ItemNote> = suspendTransaction {
        ItemNoteDAO.all().map { it.toItemNote() }
    }

    override suspend fun delete(t: ItemNote): Boolean = suspendTransaction {
        1 == ItemNoteTable.deleteWhere { ItemNoteTable.id eq t.id }
    }

    override suspend fun update(t: ItemNote): ItemNote = suspendTransaction  {
        ItemNoteDAO.findByIdAndUpdate(t.id!!){
            it.note = t.note
        }?.toItemNote()!!
    }

}
