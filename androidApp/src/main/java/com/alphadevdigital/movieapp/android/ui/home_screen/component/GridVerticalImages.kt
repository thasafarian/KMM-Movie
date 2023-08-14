package com.alphadevdigital.movieapp.android.ui.home_screen.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.alphadevdigital.movieapp.domain.model.Movie

@Composable
fun GridVerticalImages(
    images: List<Movie>
) {
    LazyVerticalGrid(
        modifier = Modifier.height(300.dp),
        columns = GridCells.Adaptive(128.dp),

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
                    model = images[index].imageUrl,
                    contentDescription = null
                )
            }
        }

    )
}