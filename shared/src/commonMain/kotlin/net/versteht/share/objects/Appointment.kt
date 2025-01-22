package net.versteht.share.objects


import kotlinx.serialization.Serializable

@Serializable
data class Appointment(
    val item: Item,
    val startDate: String,
    val endDate: String,
    var confirmed: Boolean,
    var requester: User?,
    val id: Int? = null,
)
