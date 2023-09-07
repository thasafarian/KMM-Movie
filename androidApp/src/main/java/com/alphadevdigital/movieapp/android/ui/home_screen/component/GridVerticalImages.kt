package com.alphadevdigital.movieapp.android.ui.home_screen.component

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.booleanResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import coil.compose.AsyncImage
import com.alphadevdigital.movieapp.android.R
import com.alphadevdigital.movieapp.domain.model.Movie

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun GridVerticalImages(
    movies: List<Movie>
) {
    var parentSize by remember { mutableStateOf(Size.Zero) }
    var flowRowSize by remember { mutableStateOf(Size.Zero) }
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    var imageWidth  by remember { mutableStateOf(0.dp) }
    var imageHeight by remember { mutableStateOf(0.dp) }
    var spaceBetweenContent by remember { mutableStateOf(0.dp) }
    val isTab = booleanResource(id = R.bool.is_tablet)
    val isPhone = booleanResource(id = R.bool.is_phone)
    val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE
    val cellCount = if (isTab) 3 else 2

    FlowRow(
        Modifier
            .fillMaxWidth()
            .onGloballyPositioned {
                parentSize = it.parentLayoutCoordinates?.size?.toSize() ?: Size.Zero
                flowRowSize = it.size.toSize()
                println("[GridVerticalImages] parentSize: $parentSize")
                imageWidth = if (isTab) (parentSize.width * 28 / 100).dp
                else  (parentSize.width * 23 / 100).dp
                imageHeight = imageWidth * 150 / 100

                val totalImageWidth = (imageWidth * cellCount)
                val leftOverSpace = (screenWidth - totalImageWidth)
                spaceBetweenContent = leftOverSpace / cellCount
            },
        horizontalArrangement = Arrangement.spacedBy(spaceBetweenContent),
        verticalArrangement = Arrangement.spacedBy(5.dp),
    ) {
        repeat(movies.size) { index ->

            println("[GridVerticalImages] imageWidth: $imageWidth")
            println("[GridVerticalImages] imageHeight: $imageHeight")
            AsyncImage(
                modifier = Modifier
                    .size(
                        width = imageWidth,
                        height = imageHeight
                    ),
                model = movies[index].imageUrl,
                contentDescription = movies[index].title
            )
        }
    }
}