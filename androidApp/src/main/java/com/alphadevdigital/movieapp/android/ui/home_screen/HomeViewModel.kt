package com.alphadevdigital.movieapp.android.ui.home_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alphadevdigital.movieapp.domain.model.Movie
import com.alphadevdigital.movieapp.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel(), KoinComponent {

    private val getMoviesUseCase by inject<MovieUseCase>()
    var nowPlayingUIState by mutableStateOf(NowPlayingUIState())
    var popularUIState by mutableStateOf(PopularUIState())
    var topRatedUIState by mutableStateOf(TopRatedUIState())

    private var currentPage = 1
    private var limitPage = 20
    private var nowPlayingMovies = emptyList<Movie>()
    private var popularMovies = emptyList<Movie>()
    private var topRatedMovies = emptyList<Movie>()

    companion object {
        const val NOW_PLAYING = "now_playing"
        const val POPULAR = "popular"
        const val TOP_RATED = "top_rated"
    }

    fun getMovies(movieType: String = NOW_PLAYING, forceUpdate: Boolean = false) {
        viewModelScope.launch {
            setupLoadingState(movieType)
            try {
                when (movieType) {
                    NOW_PLAYING -> {
                        val resultMovies =
                            getMoviesUseCase(forceUpdate, page = currentPage, movieType)
                        if (currentPage == 1) nowPlayingMovies = resultMovies
                        else nowPlayingUIState.nowPlayingMovies + resultMovies

                        if (resultMovies.size < limitPage) nowPlayingUIState =
                            nowPlayingUIState.copy(
                                allDataIsFetched = true
                            )

                        nowPlayingUIState = nowPlayingUIState.copy(
                            loading = false,
                            refreshing = false,
                            loadFinished = resultMovies.isEmpty(),
                            nowPlayingMovies = nowPlayingMovies
                        )
                    }

                    POPULAR -> {
                        val resultMovies =
                            getMoviesUseCase(forceUpdate, page = currentPage, movieType)
                        if (currentPage == 1) popularMovies = resultMovies
                        else popularUIState.popularMovies + resultMovies

                        if (resultMovies.size < limitPage) popularUIState = popularUIState.copy(
                            allDataIsFetched = true
                        )

                        popularUIState = popularUIState.copy(
                            loading = false,
                            refreshing = false,
                            loadFinished = resultMovies.isEmpty(),
                            popularMovies = popularMovies
                        )
                    }

                    else -> {
                        val resultMovies =
                            getMoviesUseCase(forceUpdate, page = currentPage, movieType)
                        if (currentPage == 1) topRatedMovies = resultMovies
                        else topRatedUIState.topRatedMovies + resultMovies

                        if (resultMovies.size < limitPage) topRatedUIState =
                            topRatedUIState.copy(
                                allDataIsFetched = true
                            )

                        topRatedUIState = topRatedUIState.copy(
                            loading = false,
                            refreshing = false,
                            loadFinished = resultMovies.isEmpty(),
                            topRatedMovies = topRatedMovies
                        )
                    }
                }

            } catch (error: Throwable) {
                nowPlayingUIState = nowPlayingUIState.copy(
                    loading = false,
                    refreshing = false,
                    loadFinished = true,
                    errorMessage = "Could not load movies: ${error.localizedMessage}"
                )
            }
        }
    }

    private fun setupLoadingState(movieType: String) {
        when (movieType) {
            NOW_PLAYING -> {
                nowPlayingUIState = nowPlayingUIState.copy(
                    loading = true
                )
            }

            POPULAR -> {
                popularUIState = popularUIState.copy(
                    loading = true
                )
            }

            TOP_RATED -> {
                topRatedUIState = topRatedUIState.copy(
                    loading = true
                )
            }
        }
    }
}

data class NowPlayingUIState(
    var loading: Boolean = false,
    var refreshing: Boolean = false,
    var nowPlayingMovies: List<Movie> = listOf(),
    var errorMessage: String? = null,
    var loadFinished: Boolean = false,
    var allDataIsFetched: Boolean = false
)

data class PopularUIState(
    var loading: Boolean = false,
    var refreshing: Boolean = false,
    var popularMovies: List<Movie> = listOf(),
    var errorMessage: String? = null,
    var loadFinished: Boolean = false,
    var allDataIsFetched: Boolean = false
)

data class TopRatedUIState(
    var loading: Boolean = false,
    var refreshing: Boolean = false,
    var topRatedMovies: List<Movie> = listOf(),
    var errorMessage: String? = null,
    var loadFinished: Boolean = false,
    var allDataIsFetched: Boolean = false
)