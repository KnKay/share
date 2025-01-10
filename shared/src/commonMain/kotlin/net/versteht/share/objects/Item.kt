package net.versteht.share.objects
import kotlinx.serialization.Serializable

@Serializable
data class Item(
    val name: String,
    val category: Category,
    val owner: User,
    val delegatedGroup: Group?,
    val notes: List<String>?,
)
