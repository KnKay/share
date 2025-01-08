package net.versteht.share.database


import net.versteht.share.objects.Group
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import io.ktor.server.routing.*
class  GroupJdbcRepository(database: Database) : CrudRepositoryInterface<Group> {

    init {
        transaction(database) {
            SchemaUtils.create(GroupTable)
        }
    }

    override suspend fun create(t: Group): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun read(id: Int): Group?  = suspendTransaction {
        GroupDAO.all().firstOrNull()?.toGroup()
    }

    override suspend fun list(): List<Group> = suspendTransaction {
        GroupDAO.all().map{it.toGroup()}
    }

    override suspend fun delete(t: Group): Group {
        TODO("Not yet implemented")
    }

    override suspend fun update(t: Group): Group {
        TODO("Not yet implemented")
    }


}
