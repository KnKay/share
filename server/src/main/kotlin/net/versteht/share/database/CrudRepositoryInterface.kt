package net.versteht.share.database

interface CrudRepositoryInterface<T> {
    suspend fun create(t:T): Boolean
    suspend fun read(id: Int): T
    suspend fun update(t:T): T
    suspend fun delete(t:T):T
    suspend fun list(t:T):List<T>
}
