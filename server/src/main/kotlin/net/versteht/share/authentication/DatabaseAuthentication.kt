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
import io.ktor.server.routing.RoutingCall
import net.versteht.share.database.DAOtoUser
import net.versteht.share.database.UserDAO
import net.versteht.share.database.UserTable
import net.versteht.share.objects.Login
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.collections.HashMap

import kotlin.getValue

class DatabaseAuthentication(val secret: String, val issuer: String, val audience: String) : KoinComponent, AuthenticationInterface {

    //We need a database from koin...
    private val repo by inject<UserJdbcRepository>()

    override suspend fun login(login: Login): HashMap<String, String> {
        // We need to check if the user is in the db, having the password
        val found = transaction {
            return@transaction UserDAO
                .find { (UserTable.email eq login.email!!) and (UserTable.password eq login.password) and (UserTable.confirmation eq "")}
                .limit(1)
                .map(::DAOtoUser)
                .firstOrNull()
        }
        if (found == null){
            throw AuthenticationException()
        }

        return hashMapOf(
            "token" to generateToken(found, 6000000)
            ,"refreshToken" to generateToken(found, 86400000 )
        )
    }

    override suspend fun getUser(call: RoutingCall): User? {
        val id = call.principal<JWTPrincipal>()
            ?.payload
            ?.getClaim("id")
            ?.asInt()
        return repo.read(id!!)
    }

    override suspend fun refresh(call: RoutingCall): HashMap<String, String> {
        val user = getUser(call) ?: throw AuthenticationException()
        return hashMapOf(
            "token" to generateToken(user, 6000000)
            ,"refreshToken" to generateToken(user, 86400000 )
        )
    }

    private fun generateToken(user: User, expirationSeconds: Long): String = JWT.create()
        .withAudience(audience)
        .withIssuer(issuer)
        .withClaim("username", user.username)
        .withClaim("groups", user.groups!!.map { it.name })
        .withClaim("id", user.id)
        .withExpiresAt(Date(System.currentTimeMillis() + expirationSeconds))
        .sign(Algorithm.HMAC256(secret))

}
