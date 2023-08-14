package com.alphadevdigital.movieapp.data.remote

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class MovieDTO(
    val id: Int,
    val title: String,
    val overview: String,
    @SerialName("poster_path")
    val posterImage: String?,
    @SerialName("backdrop_path")
    val backdropPath: String?,
    @SerialName("release_date")
    val releaseDate: String,
    @SerialName("vote_average")
    val vote_average: Double,
)