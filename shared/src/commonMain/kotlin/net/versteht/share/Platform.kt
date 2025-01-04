package net.versteht.share

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform