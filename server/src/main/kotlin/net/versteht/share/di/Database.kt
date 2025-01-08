package net.versteht.share.di

import io.ktor.server.config.*
import org.jetbrains.exposed.sql.Database

fun database(config: ApplicationConfig): Database{
    val url = config.propertyOrNull("database.url")?.getString() ?: "jdbc:h2:mem:fake;DB_CLOSE_DELAY=-1"
    val user = config.propertyOrNull("database.user")?.getString() ?: "root"
    val driver = config.propertyOrNull("database.driver")?.getString() ?: "org.h2.Driver"
    val password = config.propertyOrNull("database.password")?.getString() ?: ""
    return Database.connect(
        url = url,
        user = user,
        driver = driver,
        password = password,
    )
}
