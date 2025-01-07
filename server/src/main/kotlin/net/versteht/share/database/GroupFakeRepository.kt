package net.versteht.share.database

import net.versteht.share.objects.Group

class  GroupFakeRepository : CrudRepositoryInterface<Group> {
    var groups = listOf(
        Group(name="users"),
        Group(name="admins")
    )
    override suspend fun create(t: Group): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun read(id: Int): Group {
        TODO("Not yet implemented")
    }

    override suspend fun list(t: Group): List<Group> {
        return groups
    }

    override suspend fun delete(t: Group): Group {
        TODO("Not yet implemented")
    }

    override suspend fun update(t: Group): Group {
        TODO("Not yet implemented")
    }
}
