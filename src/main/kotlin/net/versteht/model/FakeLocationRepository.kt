package net.versteht.model

class FakeLocationRepository : LocationRepository {

    private val locations = mutableListOf<Location>(
        Location(city = "Hamburg", postcode = "123"),
        Location(city = "Bremen", postcode = "987")
    )

    override suspend fun allLocations(): List<Location> = locations

    override suspend fun addLocation(location: Location) {
        if (getByPostcode(location.postcode) != null){
            locations.add(location)
        }
    }

    override suspend fun getByPostcode(string: String): Location? = locations.find {
        it.postcode.equals(string, ignoreCase = true)
    }

}