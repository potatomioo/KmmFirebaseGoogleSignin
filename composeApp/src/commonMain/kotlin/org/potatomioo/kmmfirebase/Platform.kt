package org.potatomioo.kmmfirebase

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform