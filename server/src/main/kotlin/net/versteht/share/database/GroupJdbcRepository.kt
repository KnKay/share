package net.versteht.share.database



import io.ktor.server.plugins.*
import io.ktor.server.routing.*
import net.versteht.share.objects.Group
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class  GroupJdbcRepository(database: Database) : CrudRepositoryInterface<Group> {

    init {
        transaction(database) {
            addLogger(StdOutSqlLogger)
            SchemaUtils.create(GroupTable)
        }
    }

    override suspend fun create(t: Group): Group = suspendTransaction {
        DAOtoGroup(GroupDAO.new {
                name = t.name
            }
        )
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

    override suspend fun delete(t: Group): Boolean = suspendTransaction {
        val count = GroupTable.deleteWhere { GroupTable.id eq t.id }
        count == 1
    }

    override suspend fun update(t: Group): Group = suspendTransaction {
        val dao = GroupDAO.findByIdAndUpdate(t.id!!){
            it.name = t.name
        }
        if (dao == null){
            throw NotFoundException("Group ${t.name} not found")
        }
        DAOtoGroup(dao)
    }

}
