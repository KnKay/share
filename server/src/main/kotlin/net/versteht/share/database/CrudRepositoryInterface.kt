package net.versteht.share.database

interface CrudRepositoryInterface<T> {
    suspend fun create(t:T): T?
    suspend fun read(id: Int): T?
    suspend fun update(t:T): T
    suspend fun delete(t:T):T
    suspend fun list():List<T>
}
