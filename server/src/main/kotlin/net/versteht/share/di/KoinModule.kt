package net.versteht.share.di

import io.ktor.server.application.*
import net.versteht.share.database.h2Database
import org.koin.dsl.*

import net.versteht.share.database.*
import net.versteht.share.objects.Group
import org.koin.core.module.dsl.singleOf


internal fun Application.getKoinModule() = module {
    val db = h2Database()

    single<CrudRepositoryInterface<Group>> {GroupJdbcRepository(db)}
}