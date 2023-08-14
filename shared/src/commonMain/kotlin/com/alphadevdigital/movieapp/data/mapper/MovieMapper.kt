package com.alphadevdigital.movieapp.data.mapper

import com.alphadevdigital.movieapp.data.remote.MovieDTO
import com.alphadevdigital.movieapp.domain.model.Movie

internal fun MovieDTO.toMovie(): Movie {
    return Movie(
        id = id,
        title = title,
        description = overview,
        imageUrl = getImageUrl(imagePath = posterImage),
        releaseDate = releaseDate,
        landscapeImageUrl = getImageUrl(imagePath = backdropPath),
        vote_average = vote_average
    )
}

private fun getImageUrl(imagePath: String?) : String  {
    return if (imagePath == null) "https://miro.medium.com/v2/resize:fit:1000/format:webp/0*-ouKIOsDCzVCTjK-.png"
    else "https://image.tmdb.org/t/p/w500/$imagePath"
}