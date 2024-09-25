package com.player.router

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.alibaba.fastjson.JSON
import com.player.model.UserViewModel
import com.player.movie.entity.MovieEntity
import com.player.movie.screen.LaunchScreen
import com.player.movie.screen.LoginScreen
import com.player.movie.screen.MainScreen
import com.player.movie.screen.MovieDetailScreen
import com.player.movie.screen.UserScreen

@Composable
fun NavHostApp(innerPadding: PaddingValues, viewModel: UserViewModel){
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = RouteList.LaunchScreen.description,
        modifier = Modifier.padding(innerPadding)){
        composable(RouteList.LaunchScreen.description){
            LaunchScreen(navController,viewModel)
        }
        composable(RouteList.LoginScreen.description){
            LoginScreen(navController,viewModel)
        }
        composable(RouteList.MainScreen.description){
            MainScreen(navController,viewModel)
        }
        composable(
            RouteList.MovieDetailScreen.description,listOf(
            // 设置默认值
            navArgument("data") {
                type = NavType.StringType
                defaultValue = ""
            }
        )) { backStackEntry ->
            val data = backStackEntry.arguments?.getString("data")
            val movieEntity = JSON.parseObject(data, MovieEntity::class.java)
            MovieDetailScreen(navController,movieEntity)
        }
        composable(RouteList.UserScreen.description){
            UserScreen(navController,viewModel)
        }
    }
}

// 路由列表
// 可以用来关联一个导航标题名称
enum class RouteList(val description: String) {
    LaunchScreen("LaunchScreen"),
    LoginScreen("LoginScreen"),
    MainScreen("MainScreen"),
    MovieDetailScreen("movieDetail?data={data}"),
    UserScreen("UserScreen"),
}