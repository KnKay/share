package net.versteht.share.model

import net.versteht.share.authentication.UserAllowed
import net.versteht.share.objects.*

fun Item.postCreate(anOwner: User){
    owner = anOwner
}

fun Item.checkRights(user: User): UserAllowed = UserAllowed(
    true,
    true,
    owner?.id == user.id,
    owner?.id == user.id,
)
