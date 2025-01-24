package net.versteht.share.objects

import kotlinx.serialization.Serializable
typealias Note = String

@Serializable
data class ItemNote(
    var item: Item,
    var note: Note,
    var id: Int? = null
)
