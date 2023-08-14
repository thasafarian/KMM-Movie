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
    val pagerState = rememberPagerState()
    val currentIndex = pagerState.currentPage
    Box(
        modifier = Modifier.background(secondaryDark),
        contentAlignment = Alignment.BottomCenter
    ) {
        HorizontalPager(
            pageCount = images.size,
            state = pagerState,
            contentPadding = PaddingValues(end = 40.dp),

            ) { page ->

            Card(
                shape = RoundedCornerShape(15.dp),
                elevation = 60.dp
            ) {
                AsyncImage(
                    modifier = Modifier
                        .width(400.dp)
                        .height(170.dp),
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