package net.versteht.share.authentication

import com.typesafe.config.ConfigException.Null
import kotlinx.coroutines.test.runTest
import net.versteht.share.database.*
import net.versteht.share.objects.User
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.Before


import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.get
import kotlin.test.Test
import kotlin.test.assertIsNot


class DatabaseAuthenticationTest : KoinTest {
    lateinit var mockModule: Module

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
        val dut = GroupJdbcRepository(Database.connect("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", driver = "org.h2.Driver"))
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
            }
            UserTable.insert {
                it[username] = "user"
                it[email] = "user@test.de"
                it[password] = "geheim"
                it[firstnames] ="user"
                it[lastname] = "mockel"
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
        val user : User = User("admin", "nix@da.de", "", "", "", null)
        val token = dut.login(user)
        assertIsNot<Null>(token, "login failed")
    }
}