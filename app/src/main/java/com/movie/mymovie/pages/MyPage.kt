package com.movie.mymovie.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.movie.mymovie.ui.theme.MymovieTheme

@Composable
fun MyPage() {
    MymovieTheme{
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("这是用户页")
        }
    }
}