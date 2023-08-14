package com.alphadevdigital.movieapp.android.ui.video_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridItemSpanScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.alphadevdigital.movieapp.domain.model.Movie

@Composable
fun PlaylistScreen(
    navController: NavController,
    viewModel: VideoViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (!viewModel.uiState.loading && viewModel.uiState.movies.isNotEmpty()) {
            VideoGridList(
                images = viewModel.uiState.movies,
                viewModel = viewModel
            )
        }
    }
}

private val span: (LazyGridItemSpanScope) -> GridItemSpan = { GridItemSpan(2) }

@Composable
fun VideoGridList(
    images: List<Movie>,
    viewModel: VideoViewModel
) {

    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Fixed(2),

        // content padding
        contentPadding = PaddingValues(
            start = 12.dp,
            top = 16.dp,
            end = 12.dp,
            bottom = 16.dp
        ),
        content = {
            items(images.size) { index ->
                AsyncImage(
                    model = images[index].landscapeImageUrl,
                    contentDescription = null
                )
            }
            if (viewModel.uiState.loading
                && !viewModel.uiState.allDataIsFetched
            ) {
                item(span = span) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    )
}