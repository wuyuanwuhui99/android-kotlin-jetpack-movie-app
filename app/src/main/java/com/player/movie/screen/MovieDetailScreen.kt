package com.player.movie.screen

import com.player.movie.component.RatingBar
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import coil.transition.CrossfadeTransition
import com.alibaba.fastjson.JSON
import com.player.R
import com.player.constant.Constant
import com.player.http.RequestUtils
import com.player.http.ResultEntity
import com.player.movie.component.CategoryComponent
import com.player.movie.component.TitleComponent
import com.player.movie.entity.CategoryEntity
import com.player.movie.entity.MovieEntity
import com.player.movie.entity.MovieStarEntity
import com.player.theme.ThemeColor
import com.player.theme.ThemeSize
import com.player.theme.ThemeStyle
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@Composable
fun MovieDetailScreen(navController: NavHostController, movieEntity: MovieEntity) {
    LazyColumn(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = ThemeSize.containerPadding, end = ThemeSize.containerPadding)
            .scrollable(
                state = rememberScrollState(0),
                orientation = Orientation.Vertical
            )
    ) {
        item {
            MovieInfoScreen(movieEntity = movieEntity)
        }
        item {
            PlotScreen(plot = movieEntity.plot)
        }

        item {
            StarScreen(movieId = movieEntity.movieId)
        }
        item {
            RecommendScreen(navController = navController,movieEntity = movieEntity)
        }

    }
}

/**
 * @desc 电影主要信息
 * @date 2024-09-22 22:54
 * @author wuwenqiang
 */
@Composable
fun MovieInfoScreen(movieEntity: MovieEntity){
    Spacer(modifier = Modifier.height(ThemeSize.containerPadding))
    Row(modifier = ThemeStyle.boxDecoration) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier
                    .size(ThemeSize.movieWidth, ThemeSize.movieHeight)
                    .clip(RoundedCornerShape(ThemeSize.middleRadius)),
                painter = rememberImagePainter(
                    data = Constant.HOST + movieEntity.localImg,
                    builder = {
                        transition(CrossfadeTransition())
                    }
                ),
                contentDescription = null
            )
            Image(
                modifier = Modifier
                    .size(ThemeSize.middleIcon, ThemeSize.middleIcon),
                painter = painterResource(id = R.mipmap.icon_detail_play),
                contentDescription = null
            )
        }
        Spacer(modifier = Modifier.width(ThemeSize.containerPadding))
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = movieEntity.movieName,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = ThemeSize.bigFontSize
                )
            )
            Spacer(modifier = Modifier.height(ThemeSize.smallMargin))
            if (movieEntity.description != "") {
                Text(
                    text = movieEntity.description.replace("\n", "").replace(" ", ""),
                    textAlign = TextAlign.Start,
                    style = TextStyle(color = ThemeColor.subTitle)
                )
                Spacer(modifier = Modifier.height(ThemeSize.smallMargin))
            }
            Text(text = movieEntity.star, style = TextStyle(color = ThemeColor.subTitle))
            Spacer(modifier = Modifier.height(ThemeSize.smallMargin))

            Text(text = movieEntity.type, style = TextStyle(color = ThemeColor.subTitle))
            Spacer(modifier = Modifier.height(ThemeSize.smallMargin))
            Row {
                RatingBar(
                    rating = movieEntity.score.toFloat(),
                )
                Text(text = movieEntity.score.toString())
            }
        }
    }
}

/**
 * @desc 剧情
 * @date 2024-09-22 22:54
 * @author wuwenqiang
 */
@Composable
fun PlotScreen(plot:String){
    Spacer(modifier = Modifier.height(ThemeSize.containerPadding))
    Column(modifier = ThemeStyle.boxDecoration) {
        TitleComponent(title = "剧情")
        Spacer(modifier = Modifier.height(ThemeSize.containerPadding))
        Text(
            text = "      $plot",
            style = TextStyle(color = ThemeColor.subTitle)
        )
    }
}

/**
 * @desc 获取演员
 * @date 2024-09-22 22:54
 * @author wuwenqiang
 */
@Composable
fun StarScreen(movieId:Long){
    Spacer(modifier = Modifier.height(ThemeSize.containerPadding))
    Column(modifier = ThemeStyle.boxDecoration) {
        TitleComponent(title = "演员")
        Spacer(modifier = Modifier.height(ThemeSize.containerPadding))
        val movieStartList = remember { mutableStateListOf<MovieStarEntity>() }
        LaunchedEffect(Unit) {
            val getStar: Call<ResultEntity> =
                RequestUtils.movieInstance.getStar(movieId)
            getStar.enqueue(object : Callback<ResultEntity> {
                override fun onResponse(
                    call: Call<ResultEntity>,
                    response: Response<ResultEntity>
                ) {
                    val starList = JSON.parseArray(
                        JSON.toJSONString(response.body()?.data ?: ""),
                        MovieStarEntity::class.java
                    )
                    movieStartList.addAll(starList)
                }

                override fun onFailure(call: Call<ResultEntity>, t: Throwable) {
                    println("错误")
                }
            })
        }
        Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
            var index = -1
            for (movieStar in movieStartList) {
                index++
                Column(modifier = Modifier.width(ThemeSize.movieWidth)) {
                    Image(
                        modifier = Modifier
                            .width(ThemeSize.movieWidth)
                            .height(ThemeSize.movieHeight)
                            .clip(RoundedCornerShape(ThemeSize.middleRadius)),
                        painter = rememberImagePainter(
                            data = Constant.HOST + movieStar.localImg,
                            builder = {
                                transition(CrossfadeTransition())
                            }
                        ),
                        contentDescription = null
                    )
                    Text(text = movieStar.starName)
                }
                if(index != movieStartList.size -1){
                    Spacer(modifier = Modifier.width(ThemeSize.containerPadding))
                }
            }
        }
    }
}

/**
 * @desc 推荐歌曲
 * @date 2024-09-22 22:54
 * @author wuwenqiang
 */
@Composable
fun RecommendScreen(navController: NavHostController,movieEntity:MovieEntity){
    Spacer(modifier = Modifier.height(ThemeSize.containerPadding))
    CategoryComponent(category = "推荐", classify = movieEntity.classify, navController = navController)
}
