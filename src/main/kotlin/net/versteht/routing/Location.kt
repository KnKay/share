package net.versteht.routing

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.response.*

import net.versteht.model.Location

private val locations = listOf<Location>(
    Location(city = "Hamburg", postcode = "123"),
    Location(city = "Bremen", postcode = "1234"),
)

fun Route.locations(){
    route("locations"){
        get {
            call.respond(
                HttpStatusCode.OK,
                locations
            )
        }
    }
}