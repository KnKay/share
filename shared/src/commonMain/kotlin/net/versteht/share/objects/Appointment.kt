package net.versteht.share.objects

import kotlinx.datetime.DateTimeUnit

data class Appointment(
    val startDate: DateTimeUnit,
    val endDate: DateTimeUnit,
    val item: Item,
)
