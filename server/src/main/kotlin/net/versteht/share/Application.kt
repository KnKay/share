package net.versteht.share

import io.github.smiley4.ktorswaggerui.SwaggerUI
import io.github.smiley4.ktorswaggerui.routing.swaggerUI
import io.github.smiley4.ktorswaggerui.routing.openApiSpec
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import net.versteht.share.di.getKoinModule
import net.versteht.share.routing.*

import org.koin.ktor.plugin.Koin

import org.koin.logger.slf4jLogger


fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)

}

internal fun Application.module() {

    install(Koin) {
        slf4jLogger()
        modules(getKoinModule())
    }


    // Install was somehow not working...
    routing {
        api()
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