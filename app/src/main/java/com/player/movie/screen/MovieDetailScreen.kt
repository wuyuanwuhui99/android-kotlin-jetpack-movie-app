package com.player.movie.screen

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.player.movie.entity.MovieEntity

@Composable
fun MovieDetailScreen(navController: NavHostController,movieEntity:MovieEntity){
    Text(text = movieEntity.movieName)
}