package net.versteht.share.client.repositories

interface CrudRepositoryInterface<T> {
    suspend fun create(t:T): T?
    suspend fun read(id: Int): T?
    suspend fun update(t:T): T
    suspend fun delete(t:T):Boolean
    suspend fun list():List<T>
}
