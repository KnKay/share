package net.versteht.share.routing

import io.ktor.http.*
import io.ktor.serialization.JsonConvertException
import io.ktor.server.request.receive
import io.ktor.server.response.*
import io.ktor.server.routing.*
import net.versteht.share.database.CrudRepositoryInterface
import net.versteht.share.authentication.AuthenticationInterface
import net.versteht.share.model.checkRights
import net.versteht.share.model.postCreate
import net.versteht.share.objects.Item
import org.koin.ktor.ext.inject

internal fun Routing.items(path: String, repo: CrudRepositoryInterface<Item> ){
        val authRepo by inject<AuthenticationInterface>()
        route(path){
            post {
                try {
                    val item = call.receive<Item>()
                    val caller = authRepo.getUser(call)
                    item.postCreate(caller!!)
                    repo.create(item)
                } catch (ex: IllegalStateException) {
                    call.respond(HttpStatusCode.BadRequest)
                } catch (ex: JsonConvertException) {
                    call.respond(HttpStatusCode.BadRequest)
                }
            }
            // READ
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
            delete {
                val item = call.receive<Item>()
                val caller = authRepo.getUser(call)
                if (item.checkRights(caller!!).delete){
                    try{
                        call.respond(repo.update(item))
                    }catch (ex: Exception)
                    {
                        call.respond(HttpStatusCode.InternalServerError)
                    }
                }
            }
            put {
                val item = call.receive<Item>()
                val caller = authRepo.getUser(call)
                if (item.checkRights(caller!!).update){
                    try{
                        call.respond(repo.update(item))
                    }catch (ex: Exception)
                    {
                        call.respond(HttpStatusCode.InternalServerError)
                    }
                }
            }
        }
}