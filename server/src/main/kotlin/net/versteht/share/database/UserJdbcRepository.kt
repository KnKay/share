package net.versteht.share.database



import io.ktor.server.routing.*
import net.versteht.share.objects.User
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.StdOutSqlLogger

class  UserJdbcRepository(database: Database) : CrudRepositoryInterface<User> {

    init {
        transaction(database) {
            addLogger(StdOutSqlLogger)
            SchemaUtils.create(UserTable)
            SchemaUtils.create(UserGroupsTable)
        }
    }

    override suspend fun create(t: User): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun read(id: Int): User?  = suspendTransaction {
        UserDAO
            .find { (UserTable.id eq id )}
            .limit(1)
            .map(::DAOtoUser)
            .firstOrNull()
    }

    override suspend fun list(): List<User> = suspendTransaction {
        UserDAO
                .all()
                .map(::DAOtoUser)
    }

    override suspend fun delete(t: User): User {
        TODO("Not yet implemented")
    }

    override suspend fun update(t: User): User {
        TODO("Not yet implemented")
    }

}
