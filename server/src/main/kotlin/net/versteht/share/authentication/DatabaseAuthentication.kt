package net.versteht.share.authentication

import com.auth0.jwt.JWT
import net.versteht.share.database.UserJdbcRepository
import net.versteht.share.objects.User
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.*
import com.auth0.jwt.algorithms.*
import io.ktor.server.config.ApplicationConfig
import net.versteht.share.database.UserDAO
import net.versteht.share.database.UserTable
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and

import kotlin.getValue

class DatabaseAuthentication(val secret: String, val issuer: String, val audience: String) : KoinComponent, AuthenticationInterface {

    //We need a database from koin...
    private val repo by inject<UserJdbcRepository>()

    //Can we inject this??


    override suspend fun register(user: User): String {
        TODO("Not yet implemented")
    }

    override suspend fun login(user: User): String? {
        // We need to check if the user is in the db, having the password
        val found = UserDAO
            .find { (UserTable.email eq user.email) and (UserTable.password eq user.password)}
            .firstOrNull()

        if (found != null){
            return ""
        }

        return JWT.create()
            .withAudience(audience)
            .withIssuer(issuer)
            .withClaim("username", user.username)
            .withClaim("groups", user.groups)
            .withExpiresAt(Date(System.currentTimeMillis() + 6000000))
            .sign(Algorithm.HMAC256(secret))
    }
}