package net.versteht.share.authentication

data class UserAllowed(
    val create: Boolean,
    val read: Boolean,
    val update: Boolean,
    val delete: Boolean,
)
