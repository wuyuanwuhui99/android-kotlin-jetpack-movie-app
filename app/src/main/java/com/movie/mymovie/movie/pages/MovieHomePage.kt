package com.movie.mymovie.movie.pages

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.alibaba.fastjson.JSON
import com.movie.mymovie.http.RequestUtils
import com.movie.mymovie.http.ResultEntity
import com.movie.mymovie.movie.component.Banner
import com.movie.mymovie.movie.component.CategoryComponent
import com.movie.mymovie.movie.component.ClassifyComponent
import com.movie.mymovie.movie.component.SearchComponent
import com.movie.mymovie.movie.entity.CategoryEntity
import com.movie.mymovie.movie.entity.MovieEntity
import com.movie.mymovie.ui.theme.Color
import com.movie.mymovie.ui.theme.MymovieTheme
import com.movie.mymovie.ui.theme.Size
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun MovieHomePage() {

    MymovieTheme {
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxWidth()
                .padding(Size.containerPadding),
        ) {
            SearchComponent(classify = "电影")
            Divider(
                color = Color.Transparent,
                modifier = Modifier
                    .height(Size.containerPadding)
                    .fillMaxWidth())
            Banner("轮播","电影")
            Divider(
                color = Color.Transparent,
                modifier = Modifier
                    .height(Size.containerPadding)
                    .fillMaxWidth())
            ClassifyComponent()
            val allCategoryLists = remember {mutableStateListOf<CategoryEntity>()}
            LaunchedEffect(Unit) {
                val allCategoryListService: Call<ResultEntity> =
                    RequestUtils.instance.getAllCategoryListByPageName("首页")
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
                CategoryComponent(category=categoryItem.category,classify=categoryItem.classify)
            }
        }
    }
}