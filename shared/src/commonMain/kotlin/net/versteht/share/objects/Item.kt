package net.versteht.share.objects
import kotlinx.serialization.Serializable

// TODO("Not happy about the var thing... Would be nice to have a better way")
@Serializable
data class Item(
    var name: String,
    val category: Category,
    var owner: User,
    val delegatedGroup: Group?,
    val notes: List<String>? = null,
    val id: Int? = null
)
