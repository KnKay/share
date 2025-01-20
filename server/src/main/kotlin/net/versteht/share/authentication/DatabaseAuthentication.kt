package net.versteht.share.authentication

import com.auth0.jwt.JWT
import net.versteht.share.database.UserJdbcRepository
import net.versteht.share.objects.User
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.*
import com.auth0.jwt.algorithms.*
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.config.ApplicationConfig
import io.ktor.server.routing.RoutingCall
import net.versteht.share.database.DAOtoUser
import net.versteht.share.database.UserDAO
import net.versteht.share.database.UserTable
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.transaction

import kotlin.getValue

class DatabaseAuthentication(val secret: String, val issuer: String, val audience: String) : KoinComponent, AuthenticationInterface {

    //We need a database from koin...
    private val repo by inject<UserJdbcRepository>()

    override suspend fun login(user: User): String? {
        // We need to check if the user is in the db, having the password
        val found = transaction {
            return@transaction UserDAO
                .find { (UserTable.email eq user.email) and (UserTable.password eq user.password) and (UserTable.confirmation neq "")}
                .limit(1)
                .map(::DAOtoUser)
                .firstOrNull()
        }
        if (found == null){
            return ""
        }

        return JWT.create()
            .withAudience(audience)
            .withIssuer(issuer)
            .withClaim("username", found.username)
            .withClaim("groups", found.groups!!.map { it.name })
            .withClaim("id", user.id)
            .withExpiresAt(Date(System.currentTimeMillis() + 6000000))
            .sign(Algorithm.HMAC256(secret))
    }

    override suspend fun getUser(call: RoutingCall): User? {
        val id = call.principal<JWTPrincipal>()
            ?.payload
            ?.getClaim("id")
            ?.asInt()
        return repo.read(id!!)
    }
}