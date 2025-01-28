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
import net.versteht.share.objects.Item
import kotlin.collections.List

class ItemRepository(val client: HttpClient, val path: String) : CrudRepositoryInterface<Item> {
    override suspend fun create(t: Item): Item? = client.post(path) {
            contentType(ContentType.Application.Json)
            setBody(t)
        }.body<Item>()

    override suspend fun read(id: Int) = client.get("${path}/${id.toString()}").body<Item>()

    override suspend fun update(t: Item): Item = client.put(path){
        contentType(ContentType.Application.Json)
        setBody(t)
    }.body<Item>()

    override suspend fun delete(t: Item): Boolean = client.delete(path){
        contentType(ContentType.Application.Json)
        setBody(t)
    }.body()

    override suspend fun list(): List<Item> = client.get(path).body<List<Item>>()

    suspend fun byCategory(category: String) = client.get("${path}/by_category/{$category}").body<List<Item>>()
}
