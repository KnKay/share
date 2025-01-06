package net.versteht.share.objects

data class Item(
    val name: String,
    val category: Category,
    val owner: User,
    val delegatedGroup: Group,
)
