package net.versteht.share.objects
import kotlinx.serialization.Serializable

@Serializable
data class Group(
    val name: String,
    val id: Int? = null
)

