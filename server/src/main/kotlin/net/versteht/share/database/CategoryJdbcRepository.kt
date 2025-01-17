package net.versteht.share.database



import io.ktor.server.routing.*
import net.versteht.share.objects.Category
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.lang.Exception

class  CategoryJdbcRepository(database: Database) : CrudRepositoryInterface<Category> {

    init {
        transaction(database) {
            addLogger(StdOutSqlLogger)
            SchemaUtils.create(CategoryTable)
        }
    }

    override suspend fun create(t: Category): Category = suspendTransaction {
        DAOtoCategory(CategoryDAO.new {
            name = t.name
            open = t.open
        })
    }

    override suspend fun read(id: Int): Category?  = suspendTransaction {
        val dao = CategoryDAO.findById(id)
        if (dao == null){
            return@suspendTransaction null
        }
        DAOtoCategory(dao)
    }

    override suspend fun list(): List<Category> = suspendTransaction {
        CategoryDAO
                .all()
                .map(::DAOtoCategory)
    }

    override suspend fun delete(t: Category): Boolean = suspendTransaction{
        val deleted = CategoryTable.deleteWhere { CategoryTable.name eq t.name }
        deleted == 1
    }

    override suspend fun update(t: Category): Category = suspendTransaction{
        val dao =  CategoryDAO.findByIdAndUpdate(t.id!!){
            it.name = t.name
            it.open = t.open
        }
        if ( dao == null ){
            throw Exception("")
        }
        DAOtoCategory(dao)
    }

}
