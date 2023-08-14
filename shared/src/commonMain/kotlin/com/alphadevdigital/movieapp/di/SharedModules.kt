package com.alphadevdigital.movieapp.di

import com.alphadevdigital.movieapp.data.remote.MovieService
import com.alphadevdigital.movieapp.data.remote.RemoteDataSource
import com.alphadevdigital.movieapp.data.repository.MovieRepositoryImpl
import com.alphadevdigital.movieapp.domain.repository.MovieRepository
import com.alphadevdigital.movieapp.domain.usecase.MovieUseCase
import com.alphadevdigital.movieapp.util.provideDispatcher
import org.koin.dsl.module


private val dataModule =  module {
    factory { RemoteDataSource(get(), get()) }
    factory { MovieService() }
}

private val dispatcher = module {
    factory { provideDispatcher() }
}

private val domainModule = module {
    single<MovieRepository> { MovieRepositoryImpl(get()) }
    factory { MovieUseCase() }
}

private val sharedModules = listOf(domainModule, dataModule, dispatcher)

fun getSharedModules() = sharedModules
