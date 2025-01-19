package net.versteht.share.model

import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.routing.RoutingCall
import net.versteht.share.objects.Item

fun Item.postCreate(call: RoutingCall){
    val principal = call.principal<JWTPrincipal>()
    val username = principal!!.payload.getClaim("username").asString()
}