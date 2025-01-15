package net.versteht.share.authentication

import io.ktor.server.application.*
import io.ktor.server.application.ApplicationCallPipeline.ApplicationPhase.Plugins
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import io.ktor.util.*
import io.ktor.util.pipeline.*
import net.versteht.share.objects.User

class RbacConfig {
    var roles: Set<Role> = emptySet()
}

typealias Role = String

class AuthenticationException(override val message: String? = null) : Throwable()
class AuthorizationException(override val message: String? = null) : Throwable()

// https://codersee.com/secure-rest-api-ktor-role-based-authorization-rbac/
//https://ktor.io/docs/server-custom-plugins.html#app-settings
// TODO("This seem to be called always? Maybe we need some other thing in on() )
val RbacAuth = createRouteScopedPlugin(
    name = "AuthorizationPlugin",
    createConfiguration = ::RbacConfig
) {
    pluginConfig.apply {

        on(AuthenticationChecked) { call ->
            if (pluginConfig.roles.isEmpty()){
             return@on
            } else {
                val tokenGroups = getRoleFromToken(call)
                for (group in pluginConfig.roles) {
                    if (tokenGroups != null) {
                        if (tokenGroups.contains(group)){
                            return@on
                        }
                    }
                }
                throw AuthorizationException("some role is missing")
            }
        }
    }
}

fun Route.withRoles(
    vararg requestedRoles: String,
    build: Route.() -> Unit
) {
    install(RbacAuth){ roles = requestedRoles.toSet() }
    build()
}

private fun getRoleFromToken(call: ApplicationCall): List<String>? =
    call.principal<JWTPrincipal>()
        ?.payload
        ?.getClaim("groups")
        ?.asList(String::class.java)
