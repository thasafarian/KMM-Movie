package com.alphadevdigital.movieapp.data.remote
import com.alphadevdigital.movieapp.data.repository.MovieRepositoryImpl
import com.alphadevdigital.movieapp.domain.repository.MovieRepository
import com.alphadevdigital.movieapp.util.provideDispatcher
import kotlinx.coroutines.runBlocking
import kotlin.test.Test

class KtorApiTest {


    @Test
    fun getApi() = runBlocking {
        val movieApiService  = MovieService()
        val result = movieApiService.getMovies(1)
        println(result)
    }

    @Test
    fun getApiFromUseCase() = runBlocking {
        val remoteDataSource = RemoteDataSource(MovieService(), provideDispatcher())
        val repository: MovieRepository = MovieRepositoryImpl(remoteDataSource)
        val result = repository.getMovies(true,1)
        println(result.toString())
    }
}