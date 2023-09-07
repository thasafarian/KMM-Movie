package com.alphadevdigital.movieapp.android.ui.home_screen.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.alphadevdigital.movieapp.android.ui.component.CaptionBox
import com.alphadevdigital.movieapp.android.ui.component.CaptionHead
import com.alphadevdigital.movieapp.android.ui.theme.secondaryDark
import com.alphadevdigital.movieapp.domain.model.Movie
import kotlinx.coroutines.delay

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LandScapeImageSlideShow(
    images: List<Movie>,
) {
    val pagerState = rememberPagerState { images.size }
    val currentIndex = pagerState.currentPage
    Box(
        modifier = Modifier.background(secondaryDark),
        contentAlignment = Alignment.BottomCenter
    ) {

        val imageWidth = (LocalConfiguration.current.screenWidthDp * 70/100).dp
        val imageHeight = imageWidth * 60/100
        val endOffset = imageWidth * 25/100

        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(end = endOffset),
            ) { page ->

            Card(
                shape = RoundedCornerShape(15.dp),
                elevation = 60.dp
            ) {
                AsyncImage(
                    modifier = Modifier
                        .width(imageWidth)
                        .height(imageHeight),
                    contentScale = ContentScale.FillBounds,
                    model = images[page].landscapeImageUrl,
                    contentDescription = null
                )
            }
        }

        CaptionBox {
            CaptionHead(
                text = images[currentIndex].title
            )
        }
    }
}