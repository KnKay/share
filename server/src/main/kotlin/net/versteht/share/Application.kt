package net.versteht.share

import io.github.smiley4.ktorswaggerui.SwaggerUI
import io.github.smiley4.ktorswaggerui.dsl.routing.route
import io.github.smiley4.ktorswaggerui.routing.swaggerUI
import io.github.smiley4.ktorswaggerui.routing.openApiSpec
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

import net.versteht.share.di.database
import net.versteht.share.di.getKoinModule

import net.versteht.share.routing.*
import org.koin.ktor.ext.inject
import io.ktor.serialization.kotlinx.json.*
import org.koin.ktor.plugin.Koin

import org.koin.logger.slf4jLogger
import kotlinx.serialization.Serializable
import net.versteht.share.database.*


@Serializable
data class test(val name: String)

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)

}


internal fun Application.module() {
    val db = database(environment.config)
    install(Koin) {
        slf4jLogger()
        modules(getKoinModule(db))
    }

    install(ContentNegotiation) {
        json()
    }
    // This is still a little bit weird... But must be outside routing...
    val groupRepo by   inject<GroupJdbcRepository>()
    val categoryRepo by   inject<CategoryJdbcRepository>()
    val userRepo by   inject<UserJdbcRepository>()
    val itemRepo by inject<ItemJdbcRepository>()
    val appointmentRepo by inject<AppointmentJdbcRepository>()
    val noteRepo by inject<NoteJdbcRepository>()
    // Install was somehow not working...

    routing {
        groups("groups", groupRepo)
        categories("categories", categoryRepo)
        users("users", userRepo)
        items("items", itemRepo)
        appointments("appointments", appointmentRepo)
        notes("notes", noteRepo)
        route{
            get("test"){
                val ret = test("me")
                call.respond(HttpStatusCode.OK, ret)
            }
        }
    }

    if (developmentMode) {
        openApi()
    }


//    configureFrameworks()
//    configureSerialization()
//    configureDatabases()
//    configureHTTP()
//    configureSecurity()
//    configureRouting()
}