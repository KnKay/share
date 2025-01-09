package net.versteht.share.database



import io.ktor.server.routing.*
import net.versteht.share.objects.Category
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.StdOutSqlLogger

class  CategoryJdbcRepository(database: Database) : CrudRepositoryInterface<Category> {

    init {
        transaction(database) {
            addLogger(StdOutSqlLogger)
            SchemaUtils.create(CategoryTable)
        }
    }

    override suspend fun create(t: Category): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun read(id: Int): Category?  = suspendTransaction {
        CategoryDAO
            .find { (CategoryTable.id eq id )}
            .limit(1)
            .map(::DAOtoCategory)
            .firstOrNull()
    }

    override suspend fun list(): List<Category> = suspendTransaction {
        CategoryDAO
                .all()
                .map(::DAOtoCategory)
    }

    override suspend fun delete(t: Category): Category {
        TODO("Not yet implemented")
    }

    override suspend fun update(t: Category): Category {
        TODO("Not yet implemented")
    }

}
