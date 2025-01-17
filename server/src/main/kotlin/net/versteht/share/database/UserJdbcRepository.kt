package net.versteht.share.database

import io.ktor.server.plugins.*
import net.versteht.share.objects.Group
import net.versteht.share.objects.User
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class  UserJdbcRepository(database: Database) : CrudRepositoryInterface<User> {

    init {
        transaction(database) {
            addLogger(StdOutSqlLogger)
            SchemaUtils.create(UserTable)
            SchemaUtils.create(UserGroupsTable)
        }
    }

    suspend fun register(user: User): Boolean  = suspendTransaction {
        UserDAO.new {
            username = user.username
            email = user.email
            password = ""
            firstnames = user.firstnames
            lastname = user.lastname
            confirmation = getRandomString(24)
        }
        return@suspendTransaction true
    }

    suspend fun confirm(confirmation: String): User = suspendTransaction {
        val user = UserDAO.findSingleByAndUpdate(UserTable.confirmation eq confirmation ) {
            it.confirmation = ""
        }
        if (user == null){
            throw NotFoundException()
        }
        commit()
        return@suspendTransaction DAOtoUser(user)
    }

    override suspend fun create(t: User): User {
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

    override suspend fun delete(t: User): Boolean = suspendTransaction {
        UserTable.deleteWhere { UserTable.id eq t.id } == 1
    }

    override suspend fun update(t: User): User = suspendTransaction {
        val dao = UserDAO.findByIdAndUpdate(t.id!!){

        }
        if (dao == null){
            throw NotFoundException("Item ${t.username} not found")
        }
        DAOtoUser(dao)
    }

    companion object {
        fun getRandomString(length: Int) : String {
            val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
            return (1..length)
                .map { allowedChars.random() }
                .joinToString("")
        }
    }
}
