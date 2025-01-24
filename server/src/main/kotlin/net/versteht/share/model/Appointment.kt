package net.versteht.share.model

import net.versteht.share.authentication.UserAllowed
import net.versteht.share.objects.Appointment
import net.versteht.share.objects.Item
import net.versteht.share.objects.User

fun Appointment.postCreate(user: User){
    requester = user
}

fun Appointment.checkRights(user: User): UserAllowed = UserAllowed(
    true,
    true,
    (requester?.id == user.id) || (item.owner?.id == user.id),
    (requester?.id == user.id) || (item.owner?.id == user.id),
)

