package com.jetbrains.spacetutorial

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform