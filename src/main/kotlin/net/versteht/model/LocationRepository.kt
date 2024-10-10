package net.versteht.model

interface LocationRepository {
    suspend fun allLocations(): List<Location>
    suspend fun addLocation(location: Location)
    suspend fun getByPostcode(string: String): Location?
}

