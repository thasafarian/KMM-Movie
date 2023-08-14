package com.alphadevdigital.movieapp.android.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.alphadevdigital.movieapp.domain.model.Movie


class MainViewModel: ViewModel() {

    var movie by mutableStateOf<Movie?>(null)
        private set

    fun setDetailMovie(newMovie: Movie) {
        movie = newMovie
    }

}