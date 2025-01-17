package net.versteht.share.database



import io.ktor.server.routing.*
import net.versteht.share.objects.Note
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.StdOutSqlLogger

class  NoteJdbcRepository(database: Database) : CrudRepositoryInterface<Note> {

    init {
        transaction(database) {
            addLogger(StdOutSqlLogger)
            SchemaUtils.create(NoteTable)
        }
    }

    override suspend fun create(t: Note): Note {
        TODO("Not yet implemented")
    }

    override suspend fun read(id: Int): Note?  = suspendTransaction {
        NoteDAO
            .find { (NoteTable.id eq id )}
            .limit(1)
            .map(::DAOtoNote)
            .firstOrNull()
    }

    override suspend fun list(): List<Note> = suspendTransaction {
        NoteDAO
                .all()
                .map(::DAOtoNote)
    }

    override suspend fun delete(t: Note): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun update(t: Note): Note {
        TODO("Not yet implemented")
    }

}
