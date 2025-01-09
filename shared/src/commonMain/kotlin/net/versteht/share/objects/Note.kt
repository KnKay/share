package net.versteht.share.objects

import kotlinx.datetime.DateTimeUnit
import kotlinx.serialization.Serializable

@Serializable
data class Note(
    val note: String,
    val item: Item?,
)
