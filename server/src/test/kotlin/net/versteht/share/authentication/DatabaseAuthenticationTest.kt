package net.versteht.share.authentication

import com.typesafe.config.ConfigException.Null
import kotlinx.coroutines.test.runTest
import net.versteht.share.database.*
import net.versteht.share.objects.Login

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.Before



import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.get
import kotlin.test.Test
import kotlin.test.assertIsNot


class DatabaseAuthenticationTest : KoinTest {


    @Before
    fun setup() {
        populate()
        val mockModule = module {
            single() { DatabaseAuthentication("test_key", "myIssuer", "testRuns") }
        }

        startKoin {
            modules(
                mockModule
            )
        }
    }
    internal fun populate(){
        transaction {
            addLogger(StdOutSqlLogger)
            SchemaUtils.create(UserTable)
            SchemaUtils.create(GroupTable)
            SchemaUtils.create(UserGroupsTable)
            GroupTable.insert {
                it[name] = "admin"
            }
            GroupTable.insert {
                it[name] = "user"
            }
            UserTable.insert {
                it[username] = "admin"
                it[email] = "admin@test.de"
                it[password] = "geheim"
                it[firstnames] ="admin"
                it[lastname] = "mockel"
                it[confirmation] = ""
            }
            UserTable.insert {
                it[username] = "user"
                it[email] = "user@test.de"
                it[password] = "geheim"
                it[firstnames] ="user"
                it[lastname] = "mockel"
                it[confirmation] = ""
            }
            UserGroupsTable.insert {
                it[user] = 1
                it[group] = 1
            }
            UserGroupsTable.insert {
                it[user] = 2
                it[group] = 2
            }
        }
    }

    @Test
    fun register() {
    }

    @Test
    fun login(): Unit = runTest {
        val dut = get<DatabaseAuthentication>()
        val user = Login("admin", "admin@test.de", "geheim")
        val token = dut.login(user)
        assertIsNot<Null>(token, "login failed")
    }


}