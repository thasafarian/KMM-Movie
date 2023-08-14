package com.alphadevdigital.movieapp.data.remote

@kotlinx.serialization.Serializable
data class MoviesResponse(
    val results: List<MovieDTO>
)