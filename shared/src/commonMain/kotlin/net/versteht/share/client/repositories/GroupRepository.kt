package net.versteht.share.client.repositories

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import net.versteht.share.objects.Group
import kotlin.collections.List

class GroupRepository(val client: HttpClient, val path: String) : CrudRepositoryInterface<Group> {
    override suspend fun create(t: Group): Group? = client.post(path) {
            contentType(ContentType.Application.Json)
            setBody(t)
        }.body<Group>()

    override suspend fun read(id: Int) = client.get("${path}/${id.toString()}").body<Group>()

    override suspend fun update(t: Group): Group = client.put(path){
        contentType(ContentType.Application.Json)
        setBody(t)
    }.body<Group>()

    override suspend fun delete(t: Group): Boolean = client.delete(path){
        contentType(ContentType.Application.Json)
        setBody(t)
    }.body()

    override suspend fun list(): List<Group> = client.get(path).body<List<Group>>()

}