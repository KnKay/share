package net.versteht.share.authentication

import io.ktor.server.routing.RoutingCall
import  net.versteht.share.objects.User
import  net.versteht.share.objects.Login

interface AuthenticationInterface {
    suspend fun login(login: Login): String?
    suspend fun getUser(call: RoutingCall): User?
}
