package com.alphadevdigital.movieapp.android.ui.video_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alphadevdigital.movieapp.domain.model.Movie
import com.alphadevdigital.movieapp.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import javax.inject.Inject

@HiltViewModel
class VideoViewModel @Inject constructor() : ViewModel(), KoinComponent {

    private val getMoviesUseCase by inject<MovieUseCase>()
    var uiState by mutableStateOf(ExploreScreenUIState())
    private var currentPage = 1
    private var limitPage = 20

    companion object {
        const val NOW_PLAYING = "now_playing"
    }

    init {
        getMovies(forceUpdate = true)
    }

    fun getMovies(movieType: String = NOW_PLAYING, forceUpdate: Boolean = false) {
        if (uiState.loading) return
        if (currentPage == 1) uiState = uiState.copy(refreshing = true)

        viewModelScope.launch {
            delay(3000)
            uiState = uiState.copy(
                loading = true
            )

            try {
                val resultMovies = getMoviesUseCase(forceUpdate, page = currentPage, movieType)
                val movies = if (currentPage == 1) resultMovies else uiState.movies + resultMovies
                currentPage += 1

                uiState = uiState.copy(
                    loading = false,
                    refreshing = false,
                    loadFinished = resultMovies.isEmpty(),
                    movies = movies
                )

                if (resultMovies.size < limitPage)  uiState = uiState.copy(
                    allDataIsFetched = true
                )

            } catch (error: Throwable){
                uiState = uiState.copy(
                    loading = false,
                    refreshing = false,
                    loadFinished = true,
                    errorMessage = "Could not load movies: ${error.localizedMessage}"
                )
            }
        }
    }
}

data class ExploreScreenUIState(
    var loading: Boolean = false,
    var refreshing: Boolean = false,
    var movies: List<Movie> = listOf(),
    var errorMessage: String? = null,
    var loadFinished: Boolean = false,
    var allDataIsFetched: Boolean = false
)