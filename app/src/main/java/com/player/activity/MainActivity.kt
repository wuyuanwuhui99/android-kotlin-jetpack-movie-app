package com.player.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import com.google.accompanist.pager.ExperimentalPagerApi
import com.player.theme.MymovieTheme
import com.player.model.UserViewModel
import com.player.router.NavHostApp

class MainActivity : ComponentActivity() {
    private val viewModel: UserViewModel by viewModels()
    @ExperimentalPagerApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MymovieTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Scaffold { innerPadding ->
                        NavHostApp(innerPadding,viewModel)
                    }
                }
            }
        }
    }

}