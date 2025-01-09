package net.versteht.share.di

import io.ktor.server.application.*

import org.koin.dsl.*

import net.versteht.share.database.*
import net.versteht.share.objects.*
import org.jetbrains.exposed.sql.Database


internal fun Application.getKoinModule(database: Database) = module {
    single {GroupJdbcRepository(database)}
    single {CategoryJdbcRepository(database)}
}