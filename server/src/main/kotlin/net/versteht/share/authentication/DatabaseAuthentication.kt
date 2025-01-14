package net.versteht.share.authentication

import com.auth0.jwt.JWT
import net.versteht.share.database.UserJdbcRepository
import net.versteht.share.objects.User
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.*
import com.auth0.jwt.algorithms.*
import io.ktor.server.config.ApplicationConfig

import kotlin.getValue

class DatabaseAuthentication(val secret: String, val issuer: String, val audience: String) : KoinComponent, AuthenticationInterface {

    //We need a database from koin...
    private val repo by inject<UserJdbcRepository>()

    //Can we inject this??


    override suspend fun register(user: User): String {
        TODO("Not yet implemented")
    }

    override suspend fun login(user: User): String? {
        return JWT.create()
            .withAudience(audience)
            .withIssuer(issuer)
            .withClaim("username", user.username)
            .withClaim("groups", user.groups)
            .withExpiresAt(Date(System.currentTimeMillis() + 6000000))
            .sign(Algorithm.HMAC256(secret))
    }
}