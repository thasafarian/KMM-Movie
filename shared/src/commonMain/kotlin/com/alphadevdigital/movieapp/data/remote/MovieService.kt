package com.alphadevdigital.movieapp.data.remote

import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

internal class MovieService: KtorApi() {

    suspend fun getMovies(page: Int = 1, type: String): MoviesResponse = client.get {
        pathUrl("movie/$type")
        parameter("page", page)
    }.body()

    suspend fun getMovie(movieId: Int): MovieDTO = client.get {
        pathUrl("movie/${movieId}")
    }.body()
}