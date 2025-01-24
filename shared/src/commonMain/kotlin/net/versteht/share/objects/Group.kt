package net.versteht.share.objects
import kotlinx.serialization.Serializable

@Serializable
data class Group(
    val name: String,
    val open: Boolean? = false,
    val id: Int? = null
)

