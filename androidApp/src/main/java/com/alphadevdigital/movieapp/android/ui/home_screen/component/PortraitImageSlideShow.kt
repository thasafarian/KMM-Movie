package com.alphadevdigital.movieapp.android.ui.home_screen.component

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
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
    val pagerState = rememberPagerState()
    val currentIndex = pagerState.currentPage
    val currentPageOffset = pagerState.currentPageOffsetFraction

    Column(modifier = Modifier.background(secondaryDark)) {
        HorizontalPager(
            pageCount = movies.size,
            state = pagerState,
            contentPadding = PaddingValues(start = 60.dp, end = 60.dp),

            ) { page ->

            val offset = maxOffset * when (page) {
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

            Card(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxHeight(0.6F)
                    .offset(y = offset)
                    .clickable {
                        mainViewModel.setDetailMovie(movies[page])
                        navController.navigate(route = "detail")
                    },
                shape = RoundedCornerShape(30.dp),
                elevation = 110.dp
            ) {
                AsyncImage(
                    model = movies[page].imageUrl,
                    contentDescription = null
                )
            }
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