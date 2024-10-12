package com.player.movie.screen

import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.alibaba.fastjson.JSON
import com.player.R
import com.player.constant.RelationType
import com.player.http.RequestUtils
import com.player.http.ResultEntity
import com.player.movie.entity.CategoryEntity
import com.player.movie.entity.MovieEntity
import com.player.movie.entity.MovieUrlEntity
import com.player.theme.MymovieTheme
import com.player.theme.ThemeColor
import com.player.theme.ThemeSize
import com.player.theme.ThemeStyle
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@Composable
fun MoviePlayerScreen(navController: NavHostController,movieEntity: MovieEntity){
    MymovieTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            Scaffold(modifier = Modifier.fillMaxSize()){
                Column(modifier = Modifier.fillMaxSize()) {
                    LaunchedEffect(Unit){
                        val savePlayRecord: Call<ResultEntity> = RequestUtils.movieInstance.savePlayRecord(movieEntity,)
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
                    WebvieScreen()

                    Column(modifier = Modifier
                        .padding(ThemeSize.containerPadding)
                        .scrollable(
                            state = rememberScrollState(0),
                            orientation = Orientation.Vertical
                        )
                        .weight(1F, true)) {
                        MenuScreen(movieEntity)
                        Spacer(modifier = Modifier.height(ThemeSize.smallMargin))
                        MovieUrlSreen(movieEntity.id)
                    }
                }
            }
        }
    }
}

@Composable
fun WebvieScreen(){
    Column(modifier = Modifier
        .fillMaxWidth()
        .aspectRatio((16 / 9).toFloat())) {
        WebView(LocalContext.current).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            webViewClient = WebViewClient()
            loadUrl("www.baidu.com")
        }
    }
}

@Composable
fun MenuScreen(movieEntity: MovieEntity){
    val getCommentCount: Call<ResultEntity> = RequestUtils.movieInstance.getCommentCount(movieEntity.id,
        RelationType.MOVIE.name
    )
    var commentCount by remember { mutableStateOf(0) }
    LaunchedEffect(Unit){
        getCommentCount.enqueue(object : Callback<ResultEntity> {
            override fun onResponse(
                call: Call<ResultEntity>,
                response: Response<ResultEntity>
            ) {
                commentCount =(response.body()?.data as Double).toInt()
            }

            override fun onFailure(call: Call<ResultEntity>, t: Throwable) {
                println("错误")
            }
        })
    }

    Row(modifier = ThemeStyle.boxDecoration) {
        Image(
            painter = painterResource(id = R.mipmap.icon_comment),
            modifier = Modifier
                .size(ThemeSize.middleIcon),
            contentDescription = ""
        )
        Spacer(modifier = Modifier.height(ThemeSize.smallMargin))
        Text(text = commentCount.toString())
    }
}

@Composable
fun MovieUrlSreen(movieId:Long){
//    val getMovieUrl: Call<ResultEntity> = RequestUtils.movieInstance.getMovieUrl(movieId)
    val getMovieUrl: Call<ResultEntity> = RequestUtils.movieInstance.getMovieUrl(72667)
    val movieUrlEntityGroup = remember { mutableStateListOf<List<MovieUrlEntity>>() }
    LaunchedEffect(Unit){
        getMovieUrl.enqueue(object : Callback<ResultEntity> {
            override fun onResponse(
                call: Call<ResultEntity>,
                response: Response<ResultEntity>
            ) {
                val movieList = JSON.parseArray(
                    JSON.toJSONString(response.body()?.data ?: ""),
                    MovieUrlEntity::class.java
                )
                if(movieList != null){
                    for(item in movieList){
                        var movieUrlList: List<MovieUrlEntity>? = movieUrlEntityGroup.find{
                            it.isNotEmpty() && it[0].playGroup == item.playGroup
                        }
                        if (movieUrlList == null) {
                            movieUrlList = listOf(item)
                            movieUrlEntityGroup.add(movieUrlList)
                        }else{
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
                for (item in movieList){
                    index++
                    Row() {
                        Button(
                            shape = RoundedCornerShape(ThemeSize.superRadius),
                            border = BorderStroke(1.dp, ThemeColor.borderColor),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = ThemeColor.transparent),
                            modifier = Modifier
                                .height(ThemeSize.inputHeight),
                            onClick = {

                            }) {
                            Text(text = item.label,style = TextStyle(color = ThemeColor.normalColor))
                        }
                    }
                    if(index != movieList.size - 1){
                        Spacer(modifier = Modifier.width(ThemeSize.containerPadding))
                    }
                }
            }
            if(i != movieUrlEntityGroup.size - 1){
                Spacer(modifier = Modifier.width(ThemeSize.containerPadding))
                Divider(modifier = Modifier
                    .fillMaxWidth()
                    .background(ThemeColor.colorBg)
                    .height(1.dp))
                Spacer(modifier = Modifier.width(ThemeSize.containerPadding))
            }
        }
    }
}