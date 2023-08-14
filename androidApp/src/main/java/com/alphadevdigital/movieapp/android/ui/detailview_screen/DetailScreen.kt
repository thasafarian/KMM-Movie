package com.alphadevdigital.movieapp.android.ui.detailview_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.alphadevdigital.movieapp.android.ui.MainViewModel
import com.alphadevdigital.movieapp.android.ui.component.Title
import com.alphadevdigital.movieapp.android.ui.component.YoutubeScreen

@Composable
fun DetailScreen(
    viewModel: MainViewModel
) {
    val isMoviePlayed = remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        // Movie Title
        Title(
            title = viewModel.movie!!.title
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Screen Image
        if (isMoviePlayed.value) {
            YoutubeScreen(videoId = "uYPbbksJxIg") // this is hardcoded id from oppenheimer trailer
        } else {
            AsyncImage(
                model = viewModel.movie!!.landscapeImageUrl,
                contentDescription = viewModel.movie!!.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            )
        }

        // Play Button
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    isMoviePlayed.value = !isMoviePlayed.value
                },
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(16.dp)),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue)
            ) {
                if (!isMoviePlayed.value) {
                    Text(
                        text = "Play Trailer",
                        color = Color.White,
                        modifier = Modifier.padding(8.dp)
                    )
                } else {
                    Text(
                        text = "Stop Playing",
                        color = Color.White,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }

        // Description
        androidx.compose.material.Text(
            modifier = Modifier.padding(16.dp),
            text = viewModel.movie!!.description,
            style = TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Normal,
                color = Color.White,
                fontSize = 12.sp
            ),
        )
    }
}