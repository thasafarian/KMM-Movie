package com.alphadevdigital.movieapp.android.ui.explore_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.SearchBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.isContainer
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.alphadevdigital.movieapp.android.ui.MainViewModel
import com.alphadevdigital.movieapp.android.ui.explore_screen.ExploreScreenViewModel.Companion.NOW_PLAYING
import com.alphadevdigital.movieapp.android.ui.theme.yellowStar

import com.alphadevdigital.movieapp.domain.model.Movie

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExploreScreen(
    navController: NavController,
    mainViewModel: MainViewModel,
    viewModel: ExploreScreenViewModel = hiltViewModel()
) {
    var text by rememberSaveable { mutableStateOf("") }
    var active by rememberSaveable { mutableStateOf(false) }
    val filteredList = if (text.isNotEmpty()) {
        viewModel.uiState.movies.filter {
            it.title.equals(text, ignoreCase = true) || it.title.contains(text, ignoreCase = true)
        }
    } else {
        viewModel.uiState.movies
    }

    Box(Modifier.fillMaxSize()) {
        Box(
            Modifier
                .semantics { isContainer = true }
                .zIndex(1f)
                .fillMaxWidth()) {
            SearchBar(
                modifier = Modifier.align(Alignment.TopCenter),
                query = text,
                onQueryChange = { text = it },
                onSearch = { active = false },
                active = active,
                onActiveChange = {
                    active = it
                },
                placeholder = { androidx.compose.material3.Text("Search movie here..") },
                leadingIcon = { },
                trailingIcon = {
                    if (active) {
                        IconButton(onClick = {
                            active = false
                        }) {
                            Icon(Icons.Default.Close, contentDescription = null)
                        }
                    } else {
                        IconButton(onClick = {
                            active = true
                        }) {
                            Icon(Icons.Default.Search, contentDescription = null)
                        }
                    }
                },
            ) {
                if (viewModel.uiState.movies.isNotEmpty()) {
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        items(filteredList.size) { idx ->
                            val resultText = filteredList[idx].title
                            ListItem(
                                headlineContent = { androidx.compose.material3.Text(resultText) },
                                supportingContent = { androidx.compose.material3.Text("Additional info") },
                                leadingContent = {
                                    Icon(
                                        Icons.Filled.Star,
                                        contentDescription = null
                                    )
                                },
                                modifier = Modifier.clickable {
                                    text = resultText
                                    active = false
                                }
                            )
                        }
                    }
                }
            }
        }

        LazyColumn(
            contentPadding = PaddingValues(start = 16.dp, top = 72.dp, end = 16.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (viewModel.uiState.movies.isNotEmpty()) {
                items(count = filteredList.size) { index ->
                    ExploreMovieItem(
                        mainViewModel = mainViewModel,
                        navController = navController,
                        movie = filteredList[index],
                    )
                }
            }

            // Get movies data from API
            item {
                if (filteredList.size == viewModel.uiState.movies.size
                    && (!viewModel.uiState.allDataIsFetched)
                ) {
                    LaunchedEffect(key1 = Unit, block = {
                        viewModel.getMovies(movieType = NOW_PLAYING, forceUpdate = true)
                    })
                }
            }

            if (filteredList.size == viewModel.uiState.movies.size
                && viewModel.uiState.loading
                && !viewModel.uiState.allDataIsFetched
            ) {
                item {
                    MyCircularProgressView()
                }
            }
        }
    }
}

@Composable
fun ExploreMovieItem(
    mainViewModel: MainViewModel,
    navController: NavController,
    movie: Movie
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable {
                mainViewModel.setDetailMovie(movie)
                navController.navigate("detail")
            }
    ) {
        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Start
        ) {
            AsyncImage(
                model = movie.landscapeImageUrl,
                contentDescription = null,
                modifier = Modifier
                    .width(150.dp)
                    .fillMaxHeight(),
            )

            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 14.dp)
            ) {
                // Movie Title
                androidx.compose.material3.Text(
                    movie.title,
                    color = Color.Black,
                    style = TextStyle(
                        fontSize = 14.sp
                    )
                )

                // Rating Text with Star Icon
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    androidx.compose.material3.Text(
                        "Rating : ${movie.vote_average}",
                        color = Color.Black,
                        style = TextStyle(
                            fontSize = 14.sp
                        )
                    )
                    Spacer(Modifier.width(4.dp))
                    Icon(
                        Icons.Filled.Star,
                        contentDescription = null,
                         Modifier.width(15.dp),
                        tint = yellowStar
                    )
                }
            }
        }
    }
}

@Composable
private fun MyCircularProgressView() {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CircularProgressIndicator()
    }
}
