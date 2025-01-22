package net.versteht.share.routing

import io.ktor.http.*
import io.ktor.serialization.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import net.versteht.share.database.CrudRepositoryInterface
import net.versteht.share.objects.Note


internal fun Routing.notes(path: String, repo: CrudRepositoryInterface<Note> ){
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
            authenticate {
                post {
                    try {
                        val item = call.receive<Note>()
                        repo.create(item)
                    } catch (ex: IllegalStateException) {
                        call.respond(HttpStatusCode.BadRequest)
                    } catch (ex: JsonConvertException) {
                        call.respond(HttpStatusCode.BadRequest)
                    }
                }
                delete {
                    val item = call.receive<Note>()


                        try{
                            call.respond(repo.update(item))
                        }catch (ex: Exception)
                        {
                            call.respond(HttpStatusCode.InternalServerError)
                        }

                }
                put {
                    val item = call.receive<Note>()


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
