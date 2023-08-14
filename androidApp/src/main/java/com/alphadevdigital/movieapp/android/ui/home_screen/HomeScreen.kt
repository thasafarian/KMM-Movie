package com.alphadevdigital.movieapp.android.ui.home_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.alphadevdigital.movieapp.android.ui.MainViewModel
import com.alphadevdigital.movieapp.android.ui.component.Title
import com.alphadevdigital.movieapp.android.ui.home_screen.HomeViewModel.Companion.NOW_PLAYING
import com.alphadevdigital.movieapp.android.ui.home_screen.HomeViewModel.Companion.POPULAR
import com.alphadevdigital.movieapp.android.ui.home_screen.HomeViewModel.Companion.TOP_RATED
import com.alphadevdigital.movieapp.android.ui.home_screen.component.GridVerticalImages
import com.alphadevdigital.movieapp.android.ui.home_screen.component.LandScapeImageSlideShow
import com.alphadevdigital.movieapp.android.ui.home_screen.component.PortraitImageSlideShow
import com.alphadevdigital.movieapp.android.ui.theme.mainDark

@Composable
fun HomeScreen(
    navController: NavController,
    mainViewModel: MainViewModel,
    viewModel: HomeViewModel = hiltViewModel()
) {

    LaunchedEffect(key1 = 0) {
        viewModel.getMovies(movieType = NOW_PLAYING, forceUpdate = true)
        viewModel.getMovies(movieType = POPULAR, forceUpdate = true)
        viewModel.getMovies(movieType = TOP_RATED, forceUpdate = true)
    }

    Column(
        modifier = Modifier
            .background(mainDark)
            .padding(
                top = 0.dp,
                start = 8.dp,
                end = 8.dp,
                bottom = 8.dp
            )
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Title Now Playing
        Title(
            title = "Now Playing in Cinemas",
            subtitle = "Films that are playing in your local theaters now"
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Movies poster slider
        if (!viewModel.nowPlayingUIState.loading && viewModel.nowPlayingUIState.nowPlayingMovies.isNotEmpty()) {
            PortraitImageSlideShow(
                movies = viewModel.nowPlayingUIState.nowPlayingMovies,
                navController = navController,
                mainViewModel = mainViewModel
            )
        } else {
            CircularProgressIndicator()
        }

        // Title Trending
        Title(title = "Trending")
        Spacer(modifier = Modifier.height(8.dp))

        // Movies banner slider
        if (!viewModel.popularUIState.loading && viewModel.popularUIState.popularMovies.isNotEmpty()) {
            LandScapeImageSlideShow(images = viewModel.popularUIState.popularMovies)
        } else {
            CircularProgressIndicator()
        }

        // Title Top Rated
        Title(title = "Top Rated")
        Spacer(modifier = Modifier.height(8.dp))
        // Movies grid list
        if (!viewModel.topRatedUIState.loading && viewModel.topRatedUIState.topRatedMovies.isNotEmpty()) {
            GridVerticalImages(images = viewModel.topRatedUIState.topRatedMovies)
        } else {
            CircularProgressIndicator()
        }
    }
}



