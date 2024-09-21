package com.player.movie.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.player.model.UserViewModel
import com.player.theme.MymovieTheme

@Composable
fun LoginScreen(navController: NavHostController,viewModel: UserViewModel){
    MymovieTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            Scaffold(modifier = Modifier.fillMaxSize()){ innerPadding ->
                Text(text = "登录",textAlign = TextAlign.Center,//文字居中对齐
                    modifier = Modifier.fillMaxSize().wrapContentHeight(Alignment.CenterVertically)
                )
            }
        }
    }
}