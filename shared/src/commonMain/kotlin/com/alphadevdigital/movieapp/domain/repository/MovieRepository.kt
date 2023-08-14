package com.alphadevdigital.movieapp.domain.repository

import com.alphadevdigital.movieapp.domain.model.Movie

interface MovieRepository {
    suspend fun getMovies(forceUpdate: Boolean, page: Int, type: String): List<Movie>

    suspend fun getMovie(movieId: Int): Movie
}