package net.versteht.share.authentication

import io.ktor.server.routing.RoutingCall
import  net.versteht.share.objects.User

interface AuthenticationInterface {
    suspend fun login(user: User): String?
    suspend fun getUser(call: RoutingCall): User?
}
