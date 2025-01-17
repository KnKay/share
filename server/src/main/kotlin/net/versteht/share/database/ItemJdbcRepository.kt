package net.versteht.share.database



import io.ktor.server.routing.*
import net.versteht.share.objects.Item
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.StdOutSqlLogger

class  ItemJdbcRepository(database: Database) : CrudRepositoryInterface<Item> {

    init {
        transaction(database) {
            addLogger(StdOutSqlLogger)
            SchemaUtils.create(ItemTable)
        }
    }

    override suspend fun create(t: Item): Item {
        TODO("Not yet implemented")
    }

    override suspend fun read(id: Int): Item?  = suspendTransaction {
        ItemDAO
            .find { (ItemTable.id eq id )}
            .limit(1)
            .map(::DAOtoItem)
            .firstOrNull()
    }

    override suspend fun list(): List<Item> = suspendTransaction {
        ItemDAO
                .all()
                .map(::DAOtoItem)
    }

    override suspend fun delete(t: Item): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun update(t: Item): Item {
        TODO("Not yet implemented")
    }

}
