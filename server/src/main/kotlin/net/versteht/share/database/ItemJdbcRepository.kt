package net.versteht.share.database



import io.ktor.client.engine.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.plugins.*
import io.ktor.server.routing.*
import net.versteht.share.objects.Group
import net.versteht.share.objects.Item
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class  ItemJdbcRepository(database: Database) : CrudRepositoryInterface<Item> {

    init {
        transaction(database) {
            addLogger(StdOutSqlLogger)
            SchemaUtils.create(ItemTable)
        }
    }

    override suspend fun create(t: Item): Item = suspendTransaction {
        var delegation: GroupDAO? = null
        if (t.delegatedGroup != null){
            val test = GroupDAO.findById(t.delegatedGroup?.id!!)
            delegation = test
        }
        DAOtoItem(ItemDAO.new {
            name = t.name
            category = CategoryDAO.findById(t.category.id!!)!!
            owner = UserDAO.find(UserTable.id eq t.owner.id).firstOrNull()!!
            delegatedGroup = delegation
        })
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

    override suspend fun delete(t: Item): Boolean = suspendTransaction {
        val removed = ItemTable.deleteWhere { ItemTable.id eq t.id }
        removed == 1
    }

    override suspend fun update(t: Item): Item = suspendTransaction {
        val dao = ItemDAO.findByIdAndUpdate(t.id!!){
            it.name = t.name
            it.category = CategoryDAO.findById(t.category.id!!)!!
            it.delegatedGroup = GroupDAO.findById(t.delegatedGroup?.id!!)
        }
        if (dao == null){
            throw NotFoundException("Item ${t.name} not found")
        }
        DAOtoItem(dao)
    }

}
