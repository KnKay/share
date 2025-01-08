package net.versteht.share.routing

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import net.versteht.share.Greeting
import io.ktor.server.plugins.openapi.*

internal fun Routing.api() {
    greeting()
}


internal fun Routing.greeting(){
    get("greeting") {
        call.respondText("Ktor: ${Greeting().greet()}")
    }
}
