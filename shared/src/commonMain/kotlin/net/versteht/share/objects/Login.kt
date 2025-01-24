package net.versteht.share.objects

import kotlinx.serialization.Serializable

@Serializable
data class Login(
    val username: String? = null,
    val email: String? = null,
    val password: String
)
