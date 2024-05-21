package com.player.movie.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.player.R
import com.player.model.UserViewModel
import com.player.theme.Color
import com.player.theme.MymovieTheme
import com.player.theme.Size

@Composable
fun MainScreen(navController: NavHostController,viewModel: UserViewModel){

    val items = listOf(
        NavigationItem.Home,
        NavigationItem.Movie,
        NavigationItem.TV,
        NavigationItem.My
    )

    var bottomSelectedState by remember { mutableStateOf(0) }

    MymovieTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            Scaffold(
                bottomBar = {
                    BottomNavigation (
                        backgroundColor = Color.colorWhite,
                    ){

                        val navBackStackEntry by navController.currentBackStackEntryAsState()
                        val currentDestination = navBackStackEntry?.destination
                        items.forEachIndexed { index,item ->
                            BottomNavigationItem(
                                icon = {
                                    Image(
                                        painter = painterResource(
                                            id = if (bottomSelectedState == index) {
                                                item.selectItemRes
                                            } else {
                                                item.unSelectItemRes
                                            }
                                        ),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(Size.middleIcon)
                                            .padding(Size.miniMargin)
                                    )
                                },
                                label = {
                                    Text(
                                        text = item.title,
                                        fontWeight = FontWeight.Normal,
                                        fontSize = Size.normalFontSize,
                                        color = if (bottomSelectedState == index) {
                                            Color.selectedColor
                                        } else {
                                            Color.normalColor
                                        }
                                    )
                                },
                                selected = currentDestination?.hierarchy?.any{it.route == item.route} == true,
                                onClick = {
                                    bottomSelectedState = index;
                                    navController.navigate(item.route) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            )
                        }
                    }
                }
            ){ innerPadding ->
                NavHost(
                    navController = navController,
                    startDestination = NavigationItem.Home.route,
                    modifier = Modifier.padding(innerPadding)
                ) {
                    composable(NavigationItem.Home.route) {
                        MovieHomeScreen(viewModel,navController)
                    }
                    composable(NavigationItem.Movie.route) {
                        MovieScreen(viewModel,navController)
                    }
                    composable(NavigationItem.TV.route) {
                        TVScreen(viewModel,navController)
                    }
                    composable(NavigationItem.My.route) {
                        MyScreen(viewModel,navController)
                    }
                }
            }
        }
    }
}

sealed class NavigationItem(val route: String, val title: String, val selectItemRes: Int, val unSelectItemRes: Int) {
    object Home : NavigationItem("home", "首页", R.mipmap.icon_home_active, R.mipmap.icon_home)
    object Movie : NavigationItem("movie", "电影", R.mipmap.icon_movie_active, R.mipmap.icon_movie)
    object TV : NavigationItem("tv", "电视剧", R.mipmap.icon_tv_active, R.mipmap.icon_tv)
    object My : NavigationItem("my", "我的", R.mipmap.icon_user_active, R.mipmap.icon_user)
}