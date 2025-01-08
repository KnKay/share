package net.versteht.share.database

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

fun createFakeData(database: Database){
    transaction {
        addLogger(StdOutSqlLogger)
        SchemaUtils.create(GroupTable)
        GroupTable.insert {
            it[name] = "admin"
        }
        GroupTable.insert {
            it[name] = "user"
        }
    }
}