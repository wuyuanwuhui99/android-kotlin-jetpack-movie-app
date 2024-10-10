package com.player.movie.screen

import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.Image
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
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.alibaba.fastjson.JSON
import com.player.R
import com.player.constant.RelationType
import com.player.http.RequestUtils
import com.player.http.ResultEntity
import com.player.movie.entity.CategoryEntity
import com.player.movie.entity.MovieEntity
import com.player.theme.MymovieTheme
import com.player.theme.ThemeSize
import com.player.theme.ThemeStyle
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
                    Column(modifier = Modifier.fillMaxWidth().aspectRatio((16/9).toFloat())) {
                        WebView(LocalContext.current).apply {
                            layoutParams = ViewGroup.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT
                            )
                            webViewClient = WebViewClient()
                            loadUrl("www.baidu.com")
                        }
                    }

                    Column(modifier = Modifier.padding(ThemeSize.containerPadding).scrollable(
                        state = rememberScrollState(0),
                        orientation = Orientation.Vertical
                    ).weight(1F,true)) {
                        MenuScreen(movieEntity)
                    }
                }
            }
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