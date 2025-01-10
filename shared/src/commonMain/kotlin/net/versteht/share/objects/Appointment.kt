package net.versteht.share.objects

import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class Appointment(
    val item: Item,
    val startDate: LocalDateTime?,
    val endDate: LocalDateTime?,
    val confirmed: Boolean,
)
