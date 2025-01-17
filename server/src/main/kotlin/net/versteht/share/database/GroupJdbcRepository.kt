package net.versteht.share.database



import io.ktor.server.routing.*
import net.versteht.share.objects.Group
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.StdOutSqlLogger

class  GroupJdbcRepository(database: Database) : CrudRepositoryInterface<Group> {

    init {
        transaction(database) {
            addLogger(StdOutSqlLogger)
            SchemaUtils.create(GroupTable)
        }
    }

    override suspend fun create(t: Group): Group {
        TODO("Not yet implemented")
    }

    override suspend fun read(id: Int): Group?  = suspendTransaction {
        GroupDAO
            .find { (GroupTable.id eq id )}
            .limit(1)
            .map(::DAOtoGroup)
            .firstOrNull()
    }

    override suspend fun list(): List<Group> = suspendTransaction {
        GroupDAO
                .all()
                .map(::DAOtoGroup)
    }

    override suspend fun delete(t: Group): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun update(t: Group): Group {
        TODO("Not yet implemented")
    }

}
