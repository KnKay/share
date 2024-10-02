package net.versteht

import net.versteht.plugins.*
import io.ktor.server.application.*
import net.versteht.plugins.configureRouting
import net.versteht.plugins.configureSerialization

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureSerialization()
    configureRouting()
}
