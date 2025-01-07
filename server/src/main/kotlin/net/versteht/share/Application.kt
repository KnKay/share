package net.versteht.share

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun main(args: Array<String>) {
//    embeddedServer(Netty, port = SERVER_PORT, host = "0.0.0.0", module = Application::module)
//        .start(wait = true)
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    routing {
        get("/") {
            call.respondText("Ktor: ${Greeting().greet()}")
        }
    }
//    configureFrameworks()
//    configureSerialization()
//    configureDatabases()
//    configureHTTP()
//    configureSecurity()
//    configureRouting()
}