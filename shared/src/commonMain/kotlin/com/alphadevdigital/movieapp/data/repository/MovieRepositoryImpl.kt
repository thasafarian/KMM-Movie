package com.alphadevdigital.movieapp.data.repository

import com.alphadevdigital.movieapp.data.remote.RemoteDataSource
import com.alphadevdigital.movieapp.data.mapper.toMovie
import com.alphadevdigital.movieapp.domain.model.Movie
import com.alphadevdigital.movieapp.domain.repository.MovieRepository


internal class MovieRepositoryImpl(
    private val remoteDateSource: RemoteDataSource
): MovieRepository {

    override suspend fun getMovies(forceUpdate: Boolean, page: Int, type: String): List<Movie> {
        return if (forceUpdate) {
            remoteDateSource.getMovies(page = page, type = type).results.map {
                it.toMovie()
            }
        } else {
            emptyList()
        }
    }

    override suspend fun getMovie(movieId: Int): Movie {
        return remoteDateSource.getMovie(movieId = movieId).toMovie()
    }
}