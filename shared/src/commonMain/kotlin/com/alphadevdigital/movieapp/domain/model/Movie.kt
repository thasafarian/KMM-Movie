package com.alphadevdigital.movieapp.domain.model

data class Movie(
    val id: Int,
    var title: String,
    val description: String,
    val imageUrl: String,
    val releaseDate: String,
    val landscapeImageUrl: String,
    val vote_average: Double,
)