package net.versteht.share.authentication

import io.ktor.server.routing.RoutingCall
import  net.versteht.share.objects.User
import  net.versteht.share.objects.Login

interface AuthenticationInterface {
    suspend fun login(login: Login): HashMap<String, String>
    suspend fun getUser(call: RoutingCall): User?
    suspend fun refresh(call: RoutingCall): HashMap<String, String>
}
