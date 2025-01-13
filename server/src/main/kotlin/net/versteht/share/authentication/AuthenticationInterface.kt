package net.versteht.share.authentication

import  net.versteht.share.objects.User

interface AuthenticationInterface {
    suspend fun register(user: User): String
    suspend fun login(user: User): String?
}
