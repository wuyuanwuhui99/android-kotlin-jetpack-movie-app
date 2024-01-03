package com.movie.mymovie.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun MoviePage() {
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Text("这是电影页")
    }
}