package com.alphadevdigital.movieapp.domain.usecase

import com.alphadevdigital.movieapp.domain.model.Movie
import com.alphadevdigital.movieapp.domain.repository.MovieRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MovieUseCase : KoinComponent {
    private val repository: MovieRepository by inject()

    @Throws(Exception::class)
    suspend operator fun invoke(forceUpdate: Boolean, page: Int, type: String): List<Movie>{
        return repository.getMovies(forceUpdate, page = page, type = type)
    }

}