package net.versteht.model

import kotlinx.serialization.Serializable

@Serializable
data class Location (
    val city: String,
    val postcode: String,
//    val id: Int
)