package com.player.movie.screen

import android.content.Context
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.alibaba.fastjson.JSON
import com.player.R
import com.player.constant.RelationType
import com.player.http.RequestUtils
import com.player.http.ResultEntity
import com.player.movie.entity.MovieEntity
import com.player.movie.entity.MovieUrlEntity
import com.player.theme.MymovieTheme
import com.player.theme.ThemeColor
import com.player.theme.ThemeSize
import com.player.theme.ThemeStyle
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@Composable
fun MoviePlayerScreen(navController: NavHostController, movieEntity: MovieEntity) {
    MymovieTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            Scaffold(modifier = Modifier.fillMaxSize()) {
                Column(modifier = Modifier.fillMaxSize()) {
                    val movieUrlEntity = remember{ mutableStateOf(MovieUrlEntity())}
                    LaunchedEffect(Unit) {
                        val savePlayRecord: Call<ResultEntity> =
                            RequestUtils.movieInstance.savePlayRecord(movieEntity)
                        savePlayRecord.enqueue(object : Callback<ResultEntity> {
                            override fun onResponse(
                                call: Call<ResultEntity>,
                                response: Response<ResultEntity>
                            ) {

                            }

                            override fun onFailure(call: Call<ResultEntity>, t: Throwable) {
                                println("错误")
                            }
                        })
                    }
                    WebvieScreen(movieUrlEntity.value.url)
                    LazyColumn(
                       horizontalAlignment = Alignment.Start,
                       verticalArrangement = Arrangement.Top,
                        modifier = Modifier
                            .scrollable(
                                state = rememberScrollState(0),
                                orientation = Orientation.Vertical
                            )
                            .padding(ThemeSize.containerPadding)
                            .weight(1F)
                    ) {
                       item {
                           MenuScreen(movieEntity, LocalContext.current)
                       }
                       item {
                           Spacer(modifier = Modifier.height(ThemeSize.smallMargin))
                           MovieUrlSreen(movieEntity.id,movieUrlEntity)
                       }
                       item {
                           RecommendScreen(navController = navController,movieEntity = movieEntity)
                       }
                    }
                }
            }
        }
    }
}

@Composable
fun WebvieScreen(url:String?) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio((16 / 9.0).toFloat())
            .background(ThemeColor.normalColor)
    ) {
        if("" != url && url != null){
            WebView(LocalContext.current).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                webViewClient = WebViewClient()
                loadUrl(url)
            }
        }

    }
}

@Composable
fun MenuScreen(movieEntity: MovieEntity,context:Context) {
    val getCommentCount: Call<ResultEntity> = RequestUtils.movieInstance.getCommentCount(
        movieEntity.id,
        RelationType.MOVIE.name
    )
    var commentCount by remember { mutableStateOf(0) }
    LaunchedEffect(Unit) {
        getCommentCount.enqueue(object : Callback<ResultEntity> {
            override fun onResponse(
                call: Call<ResultEntity>,
                response: Response<ResultEntity>
            ) {
                commentCount = (response.body()?.data as Double).toInt()
            }

            override fun onFailure(call: Call<ResultEntity>, t: Throwable) {
                println("错误")
            }
        })
    }

    Row(
        verticalAlignment=Alignment.CenterVertically,
        modifier = ThemeStyle.boxDecoration) {
        Image(
            painter = painterResource(id = R.mipmap.icon_comment),
            modifier = Modifier
                .size(ThemeSize.middleIcon),
            contentDescription = ""
        )
        Spacer(modifier = Modifier.width(ThemeSize.smallMargin))
        Text(text = commentCount.toString(), modifier = Modifier.weight(1f))
        var isFavorite by remember { mutableStateOf(false) }
        LaunchedEffect(Unit) {
//            val isFavoriteCall: Call<ResultEntity> = RequestUtils.movieInstance.isFavorite(movieEntity.id)
            val isFavoriteCall: Call<ResultEntity> = RequestUtils.movieInstance.isFavorite(72667)
            isFavoriteCall.enqueue(object : Callback<ResultEntity> {
                override fun onResponse(
                    call: Call<ResultEntity>,
                    response: Response<ResultEntity>
                ) {
                    isFavorite = (response.body()?.data as Double).toFloat() > 0
                }
                override fun onFailure(call: Call<ResultEntity>, t: Throwable) {
                    println("错误")
                }
            })
        }
        Image(
            painter = painterResource(if(isFavorite)R.mipmap.icon_collection_active else R.mipmap.icon_collection),
            modifier = Modifier
                .size(ThemeSize.middleIcon)
                .clickable {
                    if (isFavorite){
                        val deleteFavoriteCall: Call<ResultEntity> = RequestUtils.movieInstance.deleteFavorite(72667)
                        deleteFavoriteCall.enqueue(object : Callback<ResultEntity> {
                            override fun onResponse(
                                call: Call<ResultEntity>,
                                response: Response<ResultEntity>
                            ) {
                                isFavorite = false
                                Toast.makeText(context,"取消收藏成功", Toast.LENGTH_SHORT).show()
                            }
                            override fun onFailure(call: Call<ResultEntity>, t: Throwable) {
                                println("错误")
                            }
                        })
                    }else{
                        val saveFavoriteCall: Call<ResultEntity> = RequestUtils.movieInstance.saveFavorite(72667)
                        saveFavoriteCall.enqueue(object : Callback<ResultEntity> {
                            override fun onResponse(
                                call: Call<ResultEntity>,
                                response: Response<ResultEntity>
                            ) {
                                isFavorite = true
                                Toast.makeText(context,"添加收藏成功", Toast.LENGTH_SHORT).show()
                            }
                            override fun onFailure(call: Call<ResultEntity>, t: Throwable) {
                                println("错误")
                            }
                        })
                    }
                }
            ,
            contentDescription = ""
        )
        Spacer(modifier = Modifier.width(ThemeSize.smallMargin))
        Image(
            painter = painterResource(id = R.mipmap.icon_share),
            modifier = Modifier
                .size(ThemeSize.middleIcon),
            contentDescription = ""
        )
    }
}

@Composable
fun MovieUrlSreen(movieId: Long,movieUrlEntity:MutableState<MovieUrlEntity>) {
//    val getMovieUrl: Call<ResultEntity> = RequestUtils.movieInstance.getMovieUrl(movieId)
    val getMovieUrl: Call<ResultEntity> = RequestUtils.movieInstance.getMovieUrl(72667)
    val movieUrlEntityGroup = remember { mutableStateListOf<List<MovieUrlEntity>>() }
    LaunchedEffect(Unit) {
        getMovieUrl.enqueue(object : Callback<ResultEntity> {
            override fun onResponse(
                call: Call<ResultEntity>,
                response: Response<ResultEntity>
            ) {
                val movieList = JSON.parseArray(
                    JSON.toJSONString(response.body()?.data ?: ""),
                    MovieUrlEntity::class.java
                )
                if (movieList != null) {
                    for (item in movieList) {
                        var movieUrlList: List<MovieUrlEntity>? = movieUrlEntityGroup.find {
                            it.isNotEmpty() && it[0].playGroup == item.playGroup
                        }
                        if (movieUrlList == null) {
                            movieUrlList = listOf(item)
                            movieUrlEntityGroup.add(movieUrlList)
                        } else {
                            movieUrlList.plus(item)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<ResultEntity>, t: Throwable) {
                println("错误")
            }
        })
    }
    Column(modifier = ThemeStyle.boxDecoration) {
        var i = -1
        for (movieList in movieUrlEntityGroup) {
            i++
            Column() {
                Text(movieList[0].playGroup)
                Spacer(modifier = Modifier.height(ThemeSize.smallMargin))
                var index = -1
                for (item in movieList) {
                    index++
                    if(movieUrlEntity.value.id == 0){
                        movieUrlEntity.value = item
                    }
                    Row() {
                        Box(
                            modifier = Modifier
                                .border(
                                    ThemeSize.borderWidth,
                                    if(movieUrlEntity.value.id == item.id) ThemeColor.selectedColor else ThemeColor.borderColor,
                                    RoundedCornerShape(ThemeSize.middleRadius)
                                )
                                .height(ThemeSize.inputHeight)
                                .padding(
                                    start = ThemeSize.containerPadding,
                                    end = ThemeSize.containerPadding
                                )
                        ) {
                            Text(
                                text = item.label,
                                style = TextStyle(
                                    color = if(movieUrlEntity.value.id == item.id)ThemeColor.selectedColor else ThemeColor.normalColor,
                                    textAlign = TextAlign.Center
                                ),
                                modifier = Modifier.align(Alignment.Center).clickable {
                                    movieUrlEntity.value = item
                                }
                            )
                        }
                    }
                    if (index != movieList.size - 1) {
                        Spacer(modifier = Modifier.width(ThemeSize.containerPadding))
                    }
                }
            }
            if (i != movieUrlEntityGroup.size - 1) {
                Spacer(modifier = Modifier.height(ThemeSize.containerPadding))
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(ThemeColor.colorBg)
                        .height(1.dp)
                )
                Spacer(modifier = Modifier.height(ThemeSize.containerPadding))
            }
        }
    }
}

