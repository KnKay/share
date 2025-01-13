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
import io.ktor.server.request.receive
import org.koin.ktor.plugin.Koin

import org.koin.logger.slf4jLogger
import kotlinx.serialization.Serializable
import net.versteht.share.authentication.DatabaseAuthentication
import net.versteht.share.database.*
import net.versteht.share.objects.User


@Serializable
data class test(val name: String)

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)

}


internal fun Application.module() {
    install(Koin) {
        slf4jLogger()
        modules(getKoinModule(environment.config))
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
    val dbAuth by inject<DatabaseAuthentication>()
    routing {
        groups("groups", groupRepo)
        categories("categories", categoryRepo)
        users("users", userRepo)
        items("items", itemRepo)
        appointments("appointments", appointmentRepo)
        notes("notes", noteRepo)
        // Routes that must not be protected!


        post("/login") {
            val user = call.receive<User>()
            val token = dbAuth.login(user)
            // do things needed to be done
            call.respond(hashMapOf("token" to token))
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