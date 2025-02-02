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
import net.versteht.share.objects.Category
import kotlin.collections.List

class CategoryRepository(val client: HttpClient, val path: String) : CrudRepositoryInterface<Category> {
    override suspend fun create(t: Category): Category? = client.post(path) {
            contentType(ContentType.Application.Json)
            setBody(t)
        }.body<Category>()

    override suspend fun read(id: Int) = client.get("${path}/${id.toString()}").body<Category>()

    override suspend fun update(t: Category): Category = client.put(path){
        contentType(ContentType.Application.Json)
        setBody(t)
    }.body<Category>()

    override suspend fun delete(t: Category): Boolean = client.delete(path){
        contentType(ContentType.Application.Json)
        setBody(t)
    }.body()

    override suspend fun list(): List<Category> = client.get(path).body<List<Category>>()

}