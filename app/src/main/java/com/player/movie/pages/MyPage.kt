package com.player.movie.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.player.model.UserViewModel
import com.player.theme.MymovieTheme

@Composable
fun MyPage(viewModel: UserViewModel) {
    MymovieTheme{
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("这是用户页")
        }
    }
}