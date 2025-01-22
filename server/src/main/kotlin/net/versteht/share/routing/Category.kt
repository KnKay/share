package net.versteht.share.routing

import io.ktor.http.*
import io.ktor.serialization.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import net.versteht.share.authentication.withRoles
import net.versteht.share.database.CrudRepositoryInterface
import net.versteht.share.model.checkRights
import net.versteht.share.model.postCreate
import net.versteht.share.objects.Category
import net.versteht.share.objects.Item
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
            //Changes are only okay for admin users
            authenticate() {
                withRoles ("admin"){
                    post {
                        try {
                            val item = call.receive<Category>()
                            repo.create(item)
                        } catch (ex: IllegalStateException) {
                            call.respond(HttpStatusCode.BadRequest)
                        } catch (ex: JsonConvertException) {
                            call.respond(HttpStatusCode.BadRequest)
                        }
                    }
                    put {
                        val item = call.receive<Category>()
                            try{
                                call.respond(repo.update(item))
                            }catch (ex: Exception)
                            {
                                call.respond(HttpStatusCode.InternalServerError)
                            }
                        }
                    }
                    delete {
                        val item = call.receive<Category>()
                        try{
                            call.respond(repo.delete(item))
                        }catch (ex: Exception)
                        {
                            call.respond(HttpStatusCode.InternalServerError)
                        }
                    }
                }
        }

}