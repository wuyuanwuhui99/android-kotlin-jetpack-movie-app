package com.player.movie.component

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.transition.CrossfadeTransition
import com.alibaba.fastjson.JSON
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.player.constant.Constant
import com.player.movie.entity.MovieEntity
import com.player.http.RequestUtils
import com.player.http.ResultEntity
import com.player.theme.Color
import com.player.theme.Size
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.delay
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * 自动轮播图
 * @param imgs 图片集合
 */
@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalPagerApi::class, InternalCoroutinesApi::class)
@Composable
fun Banner(
    category: String,
    classify: String
) {
    val movieEntityList = remember { mutableStateListOf<MovieEntity>() }
    if(movieEntityList.size > 0){
        Swipper(movieEntityList = movieEntityList)
    }else{
        val categoryListService: Call<ResultEntity> = RequestUtils.movieInstance.getCategoryList(category,classify)
        LaunchedEffect(Unit){
            categoryListService.enqueue(object : Callback<ResultEntity> {
                override fun onResponse(call: Call<ResultEntity>, response: Response<ResultEntity>) {
                    movieEntityList.addAll(JSON.parseArray(
                        JSON.toJSONString(response.body()?.data ?: ""),
                        MovieEntity::class.java
                    ).subList(0,5))
                }

                override fun onFailure(call: Call<ResultEntity>, t: Throwable) {
                    println("错误")
                }
            })
        }
    }

}


@OptIn(ExperimentalPagerApi::class)
@Composable
fun Swipper(
    movieEntityList: MutableList<MovieEntity>
) {
    var pageCount = movieEntityList.size
    val startIndex = 0//根据底部导航选中的下标改变展示的页面

    val pagerState = rememberPagerState(
        pageCount = pageCount,
        initialPage = 0,
        initialOffscreenLimit = pageCount
    )

    // 页码转换
    fun pageMapper(index: Int) = (index - startIndex).floorMod(pageCount)
    Column(
        modifier = Modifier
            .height(Size.swiperHeight)
            .fillMaxWidth()
    ) {
        Box {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .height(Size.swiperHeight)
                    .fillMaxWidth()
            ) { index ->
                // 计算页面下标
                val page = pageMapper(index)
                BannerItem(movieEntity = movieEntityList[page])
            }

            LaunchedEffect(key1 = Unit) {
                delay(3000)
                val scroller =
                    if (pagerState.currentPage + 1 == movieEntityList.size) 0 else pagerState.currentPage + 1
                pagerState.animateScrollToPage(scroller)
            }

            HorizontalPagerIndicator(
                pagerState = pagerState,
                activeColor = Color.selectedColor,
                inactiveColor = Color.disableColor,
                indicatorWidth = 10.dp,
                indicatorHeight = 4.dp,
                spacing = 5.dp,
                modifier = Modifier
                    .align(
                        Alignment.BottomCenter
                    )
                    .padding(Size.smallMargin)
            )
        }
    }

}

@Composable
fun BannerItem(movieEntity: MovieEntity) {
    Image(
        modifier = Modifier
            .fillMaxWidth()
            .height(Size.swiperHeight),
        contentScale = ContentScale.FillHeight,
        painter = rememberImagePainter(
            data = Constant.HOST + movieEntity.localImg,
            builder = {
                transition(CrossfadeTransition())
            }
        ),
        contentDescription = null
    )

}

/**
 * 转换下标
 */
private fun Int.floorMod(other: Int): Int = when (other) {
    0 -> this
    else -> this - floorDiv(other) * other
}