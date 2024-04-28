package com.player.movie.screen

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun MovieDetailScreen(navController: NavHostController,id:Int?){
    Text(text = id.toString())
}