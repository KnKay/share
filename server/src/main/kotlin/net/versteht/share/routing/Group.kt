package net.versteht.share.routing

import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import net.versteht.share.database.CrudRepositoryInterface
import net.versteht.share.objects.Group

fun Routing.groups(path: String, repo: CrudRepositoryInterface<Group>){
    route(path){
        get("{id}") {
            val id = call.parameters["id"]!!.toInt()
            val group = repo.read(id)
            if (group != null) {
                call.respond(HttpStatusCode.OK, group)
            } else {
                call.respond(HttpStatusCode.NotFound)
            }
        }
        get {
            call.respond(HttpStatusCode.OK, repo.list())
        }
    }
}