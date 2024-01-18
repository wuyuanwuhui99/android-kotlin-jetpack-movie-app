package com.movie.mymovie

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import com.movie.mymovie.entity.UserEntity
import com.movie.mymovie.http.RequestUtils
import com.movie.mymovie.http.ResultEntity
import com.movie.mymovie.pages.MovieHomePage
import com.movie.mymovie.pages.MoviePage
import com.movie.mymovie.pages.MyPage
import com.movie.mymovie.pages.TVPage
import com.movie.mymovie.ui.theme.Color;
import com.movie.mymovie.ui.theme.MymovieTheme
import com.movie.mymovie.ui.theme.Size;
import com.movie.mymovie.utils.SharedPreferencesUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    val isInit: MutableState<Boolean> = mutableStateOf(false)

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
                        BottomNavigationScreen()
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
        val userData: Call<ResultEntity> = RequestUtils.instance.userData
        userData.enqueue(object : Callback<ResultEntity> {
            override fun onResponse(call: Call<ResultEntity>, response: Response<ResultEntity>) {
                val gson = Gson()
                val body: ResultEntity? = response.body()
                if (body != null) {
                    BaseApplication.getInstance().token = body.token
                }
                if (body != null) {
                    BaseApplication.getInstance().userEntity=
                        gson.fromJson(
                            gson.toJson(body.data),
                            UserEntity::class.java
                        )
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
fun BottomNavigationScreen() {
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
                MovieHomePage()
            }
            composable(NavigationItem.Movie.route) {
                MoviePage()
            }
            composable(NavigationItem.TV.route) {
                TVPage()
            }
            composable(NavigationItem.My.route) {
                MyPage()
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