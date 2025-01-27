package net.versteht.share.client.repositories

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import net.versteht.share.objects.Group
import kotlin.collections.List

class CategoryRepository(val client: HttpClient, val path: String) : CrudRepositoryInterface<Group> {
    override suspend fun create(t: Group): Group? {
        TODO("Not yet implemented")
    }

    override suspend fun read(id: Int) = client.get("${path}/${id.toString()}").body<Group>()

    override suspend fun update(t: Group): Group {
        TODO("Not yet implemented")
    }

    override suspend fun delete(t: Group): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun list(): List<Group> = client.get(path).body<List<Group>>()

}