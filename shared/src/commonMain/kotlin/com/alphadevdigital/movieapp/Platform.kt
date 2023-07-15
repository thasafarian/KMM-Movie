package com.alphadevdigital.movieapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform