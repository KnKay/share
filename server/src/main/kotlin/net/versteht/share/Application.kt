package net.versteht.share

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

import net.versteht.share.di.getKoinModule

import net.versteht.share.routing.*
import org.koin.ktor.ext.inject
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.auth.Authentication
import io.ktor.server.request.receive
import org.koin.ktor.plugin.Koin

import org.koin.logger.slf4jLogger
import kotlinx.serialization.Serializable
import net.versteht.share.authentication.DatabaseAuthentication
import net.versteht.share.database.*
import net.versteht.share.objects.User

import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import net.versteht.share.authentication.withRoles
import net.versteht.share.objects.Login

@Serializable
data class test(val name: String)

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)

}


internal fun Application.module() {
    install(Koin) {
        slf4jLogger()
        modules(getKoinModule(environment.config))
    }

    install(ContentNegotiation) {
        json()
    }
    val secret = environment.config.property("authorization.jwt.secret").getString()
    val issuer = environment.config.property("authorization.jwt.issuer").getString()
    val audience = environment.config.property("authorization.jwt.audience").getString()
    val myRealm = environment.config.property("authorization.jwt.realm").getString()
    install(Authentication){
        jwt() {
            realm = myRealm
            verifier(JWT
                .require(Algorithm.HMAC256(secret))
                .withAudience(audience)
                .withIssuer(issuer)
                .build()
            )
            validate { credential ->
                if (credential.payload.getClaim("username").asString() != "") {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }
            challenge { defaultScheme, realm ->
                call.respond(HttpStatusCode.Unauthorized, "Token is not valid or has expired")
            }
        }
    }

    // This is still a little bit weird... But must be outside routing...
    val groupRepo by   inject<GroupJdbcRepository>()
    val categoryRepo by   inject<CategoryJdbcRepository>()
    val userRepo by   inject<UserJdbcRepository>()
    val itemRepo by inject<ItemJdbcRepository>()
    val appointmentRepo by inject<AppointmentJdbcRepository>()
    val noteRepo by inject<ItemNoteJdbcRepository>()
    // Install was somehow not working...
    val dbAuth by inject<DatabaseAuthentication>()
    routing {
        groups("groups", groupRepo)
        categories("categories", categoryRepo)
        users("users", userRepo)
        items("items", itemRepo)
        appointments("appointments", appointmentRepo)
        authenticate() {
            withRoles ("admin"){
                get("/pong") {
                    val principal = call.principal<JWTPrincipal>()
                    val username = principal!!.payload.getClaim("username").asString()
                    val expiresAt = principal.expiresAt?.time?.minus(System.currentTimeMillis())
                    call.respondText("Hello, $username! Token is expired at $expiresAt ms.")
                }
            }
        }
        post("/login") {
            val user = call.receive<Login>()
            val token = dbAuth.login(user)
            call.respond(hashMapOf("token" to token))
        }
        authenticate() {
            post("/refresh") {
                call.respond(dbAuth.refresh(call))
            }
            get("/ping") {
                val principal = call.principal<JWTPrincipal>()
                val username = principal!!.payload.getClaim("username").asString()
                val expiresAt = principal.expiresAt?.time?.minus(System.currentTimeMillis())
                call.respondText("Hello, $username! Token is expired at $expiresAt ms.")
            }
        }
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