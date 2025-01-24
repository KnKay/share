package net.versteht.share.di

import io.ktor.server.application.*
import io.ktor.server.config.ApplicationConfig
import net.versteht.share.authentication.DatabaseAuthentication

import org.koin.dsl.*

import net.versteht.share.database.*


internal fun Application.getKoinModule(config: ApplicationConfig) = module {
    val secret = config.property("authorization.jwt.secret").getString()
    val issuer = config.property("authorization.jwt.issuer").getString()
    val audience = config.property("authorization.jwt.audience").getString()
    val database = database(config)


    single {GroupJdbcRepository(database)}
    single {CategoryJdbcRepository(database)}
    single {UserJdbcRepository(database)}
    single {ItemJdbcRepository(database)}
    single {AppointmentJdbcRepository(database)}
    single {ItemNoteJdbcRepository(database)}
    single { DatabaseAuthentication(secret, issuer, audience) }
}