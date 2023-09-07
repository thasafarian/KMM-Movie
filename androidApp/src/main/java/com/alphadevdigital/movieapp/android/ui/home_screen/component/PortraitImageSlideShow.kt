package com.alphadevdigital.movieapp.android.ui.home_screen.component

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.booleanResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.alphadevdigital.movieapp.android.R
import com.alphadevdigital.movieapp.android.ui.MainViewModel
import com.alphadevdigital.movieapp.android.ui.component.Caption
import com.alphadevdigital.movieapp.android.ui.component.CaptionBox
import com.alphadevdigital.movieapp.android.ui.component.CaptionHead
import com.alphadevdigital.movieapp.android.ui.theme.secondaryDark
import com.alphadevdigital.movieapp.domain.model.Movie
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PortraitImageSlideShow(
    movies: List<Movie>,
    navController: NavController,
    mainViewModel: MainViewModel
) {
    val maxOffset = 30.dp
    val pagerState = rememberPagerState { movies.size }
    val currentIndex = pagerState.currentPage
    val currentPageOffset = pagerState.currentPageOffsetFraction

    val screenWidth = LocalConfiguration.current.screenWidthDp
    val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE

    val startOffset =
        if (isLandscape) ((screenWidth / 2) * 20 / 100).dp else ((screenWidth / 2) * 30 / 100).dp
    val leftOffset =
        if (isLandscape) ((screenWidth / 2) * 70 / 100).dp else ((screenWidth / 2) * 30 / 100).dp

    val isTab = booleanResource(id = R.bool.is_tablet)
    val imageWidth = if (isLandscape) (screenWidth * 50 / 100).dp else (screenWidth * 60 / 100).dp
    val imageHeight = if (isLandscape) imageWidth * 70 / 100 else imageWidth * 150 / 100

    Column(modifier = Modifier.background(secondaryDark)) {
        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(start = startOffset, end = leftOffset),
        ) { index ->

            val offset = maxOffset * when (index) {
                currentIndex -> {
                    currentPageOffset.absoluteValue
                }

                currentIndex - 1 -> {
                    1 + currentPageOffset.coerceAtMost(0f)
                }

                currentIndex + 1 -> {
                    1 - currentPageOffset.coerceAtLeast(0f)
                }

                else -> {
                    1f
                }
            }
            val imageUrl =
                if (isLandscape) movies[index].landscapeImageUrl else movies[index].imageUrl
            AsyncImage(
                modifier = Modifier
                    .offset(y = offset)
                    .width(imageWidth)
                    .height(imageHeight)
                    .clip(RoundedCornerShape(8.dp)),
                model = imageUrl,
                contentDescription = null
            )
        }

        CaptionBox {
            CaptionHead(
                text = movies[currentIndex].title
            )
            Caption(
                text = movies[currentIndex].description
            )
        }
    }
}