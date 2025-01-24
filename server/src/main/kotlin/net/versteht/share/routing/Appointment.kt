package net.versteht.share.routing

import io.ktor.http.*
import io.ktor.serialization.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import net.versteht.share.authentication.AuthenticationInterface
import net.versteht.share.database.CrudRepositoryInterface
import net.versteht.share.model.checkRights
import net.versteht.share.model.postCreate
import net.versteht.share.objects.Appointment
import net.versteht.share.objects.Item
import org.koin.ktor.ext.inject


internal fun Routing.appointments(path: String, repo: CrudRepositoryInterface<Appointment> ){
    val authRepo by inject<AuthenticationInterface>()
    route(path){
        authenticate() {
        post {
            try {
                val item = call.receive<Appointment>()
                val caller = authRepo.getUser(call)
                item.postCreate(caller!!)
                repo.create(item)
            } catch (ex: IllegalStateException) {
                call.respond(HttpStatusCode.BadRequest)
            } catch (ex: JsonConvertException) {
                call.respond(HttpStatusCode.BadRequest)
            }
        }
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
            val item = call.receive<Appointment>()
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
            val item = call.receive<Appointment>()
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
}
