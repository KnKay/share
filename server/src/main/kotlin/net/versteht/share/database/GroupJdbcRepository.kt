package net.versteht.share.database

import net.versteht.share.objects.Group

class  GroupJdbcRepository : CrudRepositoryInterface<Group> {
    override suspend fun create(t: Group): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun read(id: Int): Group {
        TODO("Not yet implemented")
    }

    override suspend fun list(t: Group): List<Group> {
        TODO("Not yet implemented")
    }

    override suspend fun delete(t: Group): Group {
        TODO("Not yet implemented")
    }

    override suspend fun update(t: Group): Group {
        TODO("Not yet implemented")
    }
}
