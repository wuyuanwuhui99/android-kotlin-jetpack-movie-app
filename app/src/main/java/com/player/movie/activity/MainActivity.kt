package com.player.movie.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.gson.Gson
import com.player.movie.entity.UserEntity
import com.player.http.RequestUtils
import com.player.http.ResultEntity
import com.player.movie.pages.MovieHomePage
import com.player.movie.pages.MoviePage
import com.player.movie.pages.MyPage
import com.player.movie.pages.TVPage
import com.player.theme.Color;
import com.player.theme.MymovieTheme
import com.player.theme.Size;
import com.player.utils.SharedPreferencesUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.navigation.compose.rememberNavController
import com.player.R
import com.player.BaseApplication
import com.player.model.UserViewModel
class MainActivity : ComponentActivity() {
    val isInit: MutableState<Boolean> = mutableStateOf(false)
    private val viewModel: UserViewModel by viewModels()
    @ExperimentalPagerApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getUserData()
        setContent {
            if(isInit.value){
                MymovieTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        BottomNavigationScreen(viewModel)
                    }
                }
            }
        }
    }

    /**
     * @author: wuwenqiang
     * @description: 获取用户信息
     * @date: 2023-12-04 15:59
     */
    private fun getUserData() {
        val userData: Call<ResultEntity> = RequestUtils.movieInstance.userData
        userData.enqueue(object : Callback<ResultEntity> {
            override fun onResponse(call: Call<ResultEntity>, response: Response<ResultEntity>) {
                val gson = Gson()
                val body: ResultEntity? = response.body()
                if (body != null) {
                    BaseApplication.getInstance().token = body.token
                }
                if (body != null) {
                    val userEntity = gson.fromJson(
                        gson.toJson(body.data),
                        UserEntity::class.java
                    )
                    BaseApplication.getInstance().userEntity = userEntity

                    viewModel.setUserEntity(userEntity)
                    SharedPreferencesUtils.setParam(
                        this@MainActivity,
                        "token",
                        body.token
                    )
                }
                isInit.value = true;
            }

            override fun onFailure(call: Call<ResultEntity>, t: Throwable) {
                println("错误")
            }
        })
    }
}


@Composable
fun BottomNavigationScreen(viewModel: UserViewModel) {
    val navController = rememberNavController()
    val items = listOf(
        NavigationItem.Home,
        NavigationItem.Movie,
        NavigationItem.TV,
        NavigationItem.My
    )

    Scaffold(
        bottomBar = {
            BottomNavigation (
                backgroundColor = Color.colorWhite,
            ){
                var bottomSelectedState by remember { mutableStateOf(0) }
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
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = NavigationItem.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(NavigationItem.Home.route) {
                MovieHomePage(viewModel)
            }
            composable(NavigationItem.Movie.route) {
                MoviePage(viewModel)
            }
            composable(NavigationItem.TV.route) {
                TVPage(viewModel)
            }
            composable(NavigationItem.My.route) {
                MyPage(viewModel)
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