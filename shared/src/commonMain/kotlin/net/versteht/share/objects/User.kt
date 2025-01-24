package net.versteht.share.objects
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val username: String,
    val email: String,
    val password: String?,
    val firstnames: String,
    val lastname: String,
    val groups: List<Group>?,
    val id: Int? = null
)
