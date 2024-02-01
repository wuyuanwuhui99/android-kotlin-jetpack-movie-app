package com.movie.mymovie.movie.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.alibaba.fastjson.JSON
import com.movie.mymovie.http.RequestUtils
import com.movie.mymovie.http.ResultEntity
import com.movie.mymovie.movie.entity.MovieEntity
import com.movie.mymovie.ui.theme.Size
import com.movie.mymovie.ui.theme.Style
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun CategoryComponent (
    category:String,
    classify:String
){
    Column(
        modifier = Style.boxDecoration
    ) {
        val isInit = remember { mutableStateOf(false) }
        val movieEntityList = remember { mutableStateListOf<MovieEntity>() }
        if(isInit.value){
            TitleComponent(title = category)

        }else {
            val categoryListService: Call<ResultEntity> =
                RequestUtils.instance.getCategoryList(category, classify)
            LaunchedEffect(Unit) {
                categoryListService.enqueue(object : Callback<ResultEntity> {
                    override fun onResponse(
                        call: Call<ResultEntity>,
                        response: Response<ResultEntity>
                    ) {
                        val movieEntityLists = JSON.parseArray(
                            JSON.toJSONString(response.body()?.data ?: ""),
                            MovieEntity::class.java
                        ).subList(0, 5)
                        for (movieEntity in movieEntityLists) {
                            movieEntityList.add(movieEntity)
                        }
                    }

                    override fun onFailure(call: Call<ResultEntity>, t: Throwable) {
                        println("错误")
                    }
                })
            }
        }
    }
}