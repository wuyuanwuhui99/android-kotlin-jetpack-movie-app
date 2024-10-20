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
import com.player.movie.component.SearchComponent
import com.player.movie.entity.CategoryEntity
import com.player.theme.ThemeColor
import com.player.theme.MymovieTheme
import com.player.theme.ThemeSize
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun TVScreen(userViewModel: UserViewModel,navController: NavHostController,value:PaddingValues) {
    MymovieTheme {
        LazyColumn(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start=ThemeSize.containerPadding, end = ThemeSize.containerPadding)
                .scrollable(
                    state = rememberScrollState(0),
                    orientation= Orientation.Vertical
                )
        ) {
            item {
                Spacer(modifier = Modifier
                    .height(ThemeSize.containerPadding))
                SearchComponent(userViewModel,"电视剧",navController)
                Spacer(modifier = Modifier
                    .height(ThemeSize.containerPadding))
                Banner("轮播","电视剧")
                Divider(
                    color = ThemeColor.transparent,
                    modifier = Modifier
                        .height(ThemeSize.containerPadding)
                        .fillMaxWidth())
                val allCategoryLists = remember {mutableStateListOf<CategoryEntity>()}
                LaunchedEffect(Unit) {
                    val allCategoryListService: Call<ResultEntity> =
                        RequestUtils.movieInstance.getAllCategoryListByPageName("电视剧")
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
                        .height(ThemeSize.containerPadding))
                    CategoryComponent(category=categoryItem.category,classify=categoryItem.classify,navController=navController)
                }
                Spacer(modifier = Modifier
                    .height(value.calculateBottomPadding() + ThemeSize.containerPadding))
            }
        }
    }
}