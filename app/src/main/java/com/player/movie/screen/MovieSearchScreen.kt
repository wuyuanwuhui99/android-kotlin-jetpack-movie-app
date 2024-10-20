package com.player.movie.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import androidx.navigation.NavHostController
import com.player.model.UserViewModel
import com.player.theme.MymovieTheme
import com.player.theme.ThemeColor
import com.player.theme.ThemeSize

@Composable
fun MovieSearchScreen( navController: NavHostController,userViewModel: UserViewModel) {
    MymovieTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            Scaffold(
                modifier = Modifier
                    .fillMaxSize()
                    .background(ThemeColor.colorBg)
                    .padding(ThemeSize.containerPadding)

            ) {


            }
        }
    }
}
