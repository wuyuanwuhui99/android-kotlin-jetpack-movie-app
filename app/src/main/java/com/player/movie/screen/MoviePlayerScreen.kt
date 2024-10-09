package com.player.movie.screen

import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.player.model.UserViewModel
import com.player.movie.entity.MovieEntity
import com.player.theme.MymovieTheme


@Composable
fun MoviePlayerScreen(navController: NavHostController,movieEntity: MovieEntity){
    MymovieTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            Scaffold(modifier = Modifier.fillMaxSize()){
                Column(modifier = Modifier.aspectRatio((16/9).toFloat())) {
                    WebView(LocalContext.current).apply {
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                        webViewClient = WebViewClient()
                        loadUrl("www.baidu.com")
                    }
                }
            }
        }
    }
}
