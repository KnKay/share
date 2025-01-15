package net.versteht.share.database

import net.versteht.share.objects.User
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Table

object UserGroupsTable : Table() {
    val user = reference("user", UserTable)
    val group = reference("group", GroupTable)
    override val primaryKey = PrimaryKey(user, group, name = "PK_user_group") // PK_StarWarsFilmActors_swf_act is optional here
}

object UserTable: IntIdTable(){
    val username = varchar("username", 50).uniqueIndex()
    val email = varchar("email", 50).uniqueIndex()
    val password = varchar("password", 50).nullable()
    val firstnames = varchar("firstnames", 50)
    val lastname = varchar("lastname", 50)
    val confirmation = varchar("confirmation", length = 24)
}

class UserDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<UserDAO>(UserTable)
    var username by UserTable.username
    var email by UserTable.email
    var password by UserTable.password
    var firstnames by UserTable.firstnames
    var lastname by UserTable.lastname
    var confirmation by UserTable.confirmation
    var groups by GroupDAO via UserGroupsTable
}

fun DAOtoUser(dao: UserDAO): User = User(dao.username, dao.email, dao.password, dao.firstnames, dao.lastname, dao.groups.map(::DAOtoGroup).toList())
