package net.versteht.share.objects
import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val name: String,
    val open: Boolean,
    val id: Int? = null
)
