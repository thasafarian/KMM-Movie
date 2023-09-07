package com.alphadevdigital.movieapp.android.ui.home_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.alphadevdigital.movieapp.android.ui.MainViewModel
import com.alphadevdigital.movieapp.android.ui.component.Title
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
    LazyColumn(
        modifier = Modifier
            .background(mainDark)
            .padding(
                top = 0.dp,
                start = 8.dp,
                end = 8.dp,
                bottom = 8.dp
            )
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        item {
            if (!viewModel.nowPlayingUIState.loading && viewModel.nowPlayingUIState.nowPlayingMovies.isNotEmpty()) {
                // Now Playing Movies Section
                Title(
                    title = "Now Playing in Cinemas",
                    subtitle = "Films that are playing in your local theaters now"
                )
                Spacer(modifier = Modifier.height(8.dp))
                PortraitImageSlideShow(
                    movies = viewModel.nowPlayingUIState.nowPlayingMovies,
                    navController = navController,
                    mainViewModel = mainViewModel
                )
            } else {
                CircularProgressIndicator()
            }
        }
        item {
            if (!viewModel.popularUIState.loading && viewModel.popularUIState.popularMovies.isNotEmpty()) {
                // Trending Movies Section
                Title(title = "Trending")
                Spacer(modifier = Modifier.height(8.dp))
                LandScapeImageSlideShow(images = viewModel.popularUIState.popularMovies)
            } else {
                CircularProgressIndicator()
            }
        }
        item {
            if (!viewModel.topRatedUIState.loading && viewModel.topRatedUIState.topRatedMovies.isNotEmpty()) {
                val movies = viewModel.topRatedUIState.topRatedMovies
                // Top Rated Movie Section
                Title(title = "Top Rated")
                Spacer(modifier = Modifier.height(8.dp))
                GridVerticalImages(movies = movies)
            } else {
                CircularProgressIndicator()
            }
        }
    }
}
