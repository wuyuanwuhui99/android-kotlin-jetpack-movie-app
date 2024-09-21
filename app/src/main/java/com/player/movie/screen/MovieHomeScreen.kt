package com.player.movie.screen

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.alibaba.fastjson.JSON
import com.player.http.RequestUtils
import com.player.http.ResultEntity
import com.player.model.UserViewModel
import com.player.movie.component.Banner
import com.player.movie.component.CategoryComponent
import com.player.movie.component.ClassifyComponent
import com.player.movie.component.SearchComponent
import com.player.movie.entity.CategoryEntity
import com.player.theme.Color
import com.player.theme.MymovieTheme
import com.player.theme.Size
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun MovieHomeScreen(userViewModel:UserViewModel,navController: NavHostController) {
    MymovieTheme {
        LazyColumn(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = Size.containerPadding, end = Size.containerPadding)
                .scrollable(
                    state = rememberScrollState(0),
                    orientation = Orientation.Vertical
                )
        ) {
            item {
                Spacer(modifier = Modifier
                    .height(Size.containerPadding))
                SearchComponent(userViewModel,classify = "电影")
                Spacer(modifier = Modifier
                    .height(Size.containerPadding))
                Banner("轮播","电影")
                Divider(
                    color = Color.transparent,
                    modifier = Modifier
                        .height(Size.containerPadding)
                        .fillMaxWidth())
                ClassifyComponent()
                val allCategoryLists = remember {mutableStateListOf<CategoryEntity>()}
                LaunchedEffect(Unit) {
                    val allCategoryListService: Call<ResultEntity> =
                        RequestUtils.movieInstance.getAllCategoryListByPageName("首页")
                    allCategoryListService.enqueue(object : Callback<ResultEntity> {
                        override fun onResponse(
                            call: Call<ResultEntity>,
                            response: Response<ResultEntity>
                        ) {
                            val allList = JSON.parseArray(
                                JSON.toJSONString(response.body()?.data ?: ""),
                                CategoryEntity::class.java
                            )
                            allCategoryLists.addAll(allList)
                        }

                        override fun onFailure(call: Call<ResultEntity>, t: Throwable) {
                            println("错误")
                        }
                    })
                }

                for (categoryItem in allCategoryLists){
                    Spacer(modifier = Modifier
                        .height(Size.containerPadding))
                    CategoryComponent(category=categoryItem.category,classify=categoryItem.classify,navController = navController)
                }
                Spacer(modifier = Modifier
                    .height(Size.containerPadding))
            }
        }
    }
}