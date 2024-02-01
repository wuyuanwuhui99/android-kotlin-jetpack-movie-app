package com.movie.mymovie.movie.pages

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.movie.mymovie.movie.component.Banner
import com.movie.mymovie.movie.component.ClassifyComponent
import com.movie.mymovie.movie.component.SearchComponent
import com.movie.mymovie.ui.theme.Color
import com.movie.mymovie.ui.theme.MymovieTheme
import com.movie.mymovie.ui.theme.Size

@Composable
fun MovieHomePage() {

    MymovieTheme {
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxWidth()
                .padding(Size.containerPadding),
        ) {
            SearchComponent(classify = "电影")
            Divider(
                color = Color.Transparent,
                modifier = Modifier
                    .height(Size.containerPadding)
                    .fillMaxWidth())
            Banner("轮播","电影")
            Divider(
                color = Color.Transparent,
                modifier = Modifier
                    .height(Size.containerPadding)
                    .fillMaxWidth())
            ClassifyComponent()
            Divider(
                color = Color.Transparent,
                modifier = Modifier
                    .height(Size.containerPadding)
                    .fillMaxWidth())

        }
    }
}