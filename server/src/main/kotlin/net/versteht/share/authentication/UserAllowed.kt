package net.versteht.share.authentication

data class UserAllowed(
    val create: Boolean = false,
    val read: Boolean = false,
    val update: Boolean = false,
    val delete: Boolean = false,
)
