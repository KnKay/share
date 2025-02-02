package net.versteht.share.database

interface FilterRepo<T> {
    suspend fun byFiler(map: Map<String, String>): List<T>?
}