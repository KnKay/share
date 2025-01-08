package net.versteht.share.database

import org.jetbrains.exposed.sql.Database

fun h2Database(): Database = Database.connect(
            url = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1",
            user = "root",
            driver = "org.h2.Driver",
            password = "",
        )
