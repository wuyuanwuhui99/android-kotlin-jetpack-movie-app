package com.movie.mymovie.movie.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import coil.compose.rememberImagePainter
import coil.transition.CrossfadeTransition
import com.alibaba.fastjson.JSON
import com.movie.mymovie.http.RequestUtils
import com.movie.mymovie.http.ResultEntity
import com.movie.mymovie.movie.api.Api
import com.movie.mymovie.movie.entity.MovieEntity
import com.movie.mymovie.ui.theme.Size
import com.movie.mymovie.ui.theme.Style
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun CategoryComponent(
    category: String,
    classify: String
) {
    Column(
        modifier = Style.boxDecoration
    ) {
        val movieEntityList = remember { mutableStateListOf<MovieEntity>() }
        TitleComponent(title = category)
        LazyRow(
            modifier = Modifier
                .scrollable(
                    state = rememberScrollState(0),
                    orientation = Orientation.Horizontal
                )
                .padding(top = Size.containerPadding)
        ) {
            item {
                var index: Int = 0;
                for (movieEntity in movieEntityList) {
                    index++;
                    Column() {
                        Image(
                            contentScale = ContentScale.FillHeight,
                            modifier = Modifier
                                .height(Size.movieHeight)
                                .width(Size.movieWidth)
                                .clip(RoundedCornerShape(Size.middleRadius)),
                            contentDescription = null,
                            painter = rememberImagePainter(
                                data = Api.HOST + movieEntity.localImg,
                                builder = {
                                    transition(CrossfadeTransition())
                                }
                            ))
                        Spacer(modifier = Modifier.height(Size.smallMargin))
                        Text(
                            text = movieEntity.movieName,
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(alignment = Alignment.CenterHorizontally)
                        )
                    }
                    if (movieEntityList.size != index) {
                        Spacer(modifier = Modifier.width(Size.containerPadding))
                    }
                }
            }

        }
        LaunchedEffect(Unit) {
            val categoryListService: Call<ResultEntity> =
                RequestUtils.instance.getCategoryList(category, classify)
            categoryListService.enqueue(object : Callback<ResultEntity> {
                override fun onResponse(
                    call: Call<ResultEntity>,
                    response: Response<ResultEntity>
                ) {
                    movieEntityList.addAll(
                        JSON.parseArray(
                            JSON.toJSONString(response.body()?.data ?: ""),
                            MovieEntity::class.java
                        )
                    )
                }

                override fun onFailure(call: Call<ResultEntity>, t: Throwable) {
                    println("错误")
                }
            })

        }
    }
}