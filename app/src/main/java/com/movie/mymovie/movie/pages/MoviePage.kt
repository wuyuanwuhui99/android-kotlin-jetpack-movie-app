package com.movie.mymovie.movie.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.movie.mymovie.ui.theme.MymovieTheme

@Composable
fun MoviePage() {
    MymovieTheme{
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("这是电影页")
        }
    }
}

