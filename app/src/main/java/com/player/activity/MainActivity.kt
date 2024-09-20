package com.player.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.gson.Gson
import com.player.movie.entity.UserEntity
import com.player.http.RequestUtils
import com.player.http.ResultEntity
import com.player.theme.MymovieTheme
import com.player.utils.SharedPreferencesUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.player.BaseApplication
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