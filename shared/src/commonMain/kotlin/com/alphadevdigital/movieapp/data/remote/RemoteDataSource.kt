package com.alphadevdigital.movieapp.data.remote

import com.alphadevdigital.movieapp.util.Dispatcher
import kotlinx.coroutines.withContext

internal class RemoteDataSource(
    private val apiService: MovieService,
    private val dispatcher: Dispatcher
) {

    suspend fun getMovies(page: Int, type: String) = withContext(dispatcher.io){
        apiService.getMovies(page = page, type = type)
    }

    suspend fun getMovie(movieId: Int) = withContext(dispatcher.io){
        apiService.getMovie(movieId = movieId)
    }
}