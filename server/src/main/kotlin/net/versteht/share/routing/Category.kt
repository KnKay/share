package net.versteht.share.routing

import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import net.versteht.share.database.CrudRepositoryInterface
import net.versteht.share.objects.Category
import org.koin.ktor.ext.inject


internal fun Routing.categories(path: String, repo: CrudRepositoryInterface<Category> ){
        route(path){
            get("/{id}") {
                val id = call.parameters["id"]!!.toInt()
                val ret = repo.read(id)
                if (ret != null) {
                    call.respond(ret)
                } else {
                    call.respond(HttpStatusCode.NotFound)
                }
            }
            get {
                call.respond(HttpStatusCode.OK, repo.list())
            }
        }
}