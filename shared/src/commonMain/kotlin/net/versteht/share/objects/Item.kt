package net.versteht.share.objects
import kotlinx.serialization.Serializable

@Serializable
data class Item(
    var name: String,
    val category: Category,
    val owner: User,
    val delegatedGroup: Group?,
    val notes: List<String>? = null,
    val id: Int? = null
)
