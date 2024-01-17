package com.movie.mymovie.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.transition.CrossfadeTransition
import com.alibaba.fastjson.JSON
import com.google.gson.Gson
import com.movie.mymovie.BaseApplication
import com.movie.mymovie.R
import com.movie.mymovie.api.Api
import com.movie.mymovie.component.Banner
import com.movie.mymovie.entity.MovieEntity
import com.movie.mymovie.http.RequestUtils
import com.movie.mymovie.http.ResultEntity
import com.movie.mymovie.ui.theme.Color
import com.movie.mymovie.ui.theme.MymovieTheme
import com.movie.mymovie.ui.theme.Size
import com.movie.mymovie.ui.theme.Style
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
            Row(
                modifier = Style.boxDecoration
            ) {
                Image(
                    modifier = Modifier
                        .size(Size.middleAvater, Size.middleAvater)
                        .clip(RoundedCornerShape(Size.middleAvater)),
                    painter = rememberImagePainter(
                        data = Api.HOST + BaseApplication.getInstance().userEntity?.avater,
                        builder = {
                            transition(CrossfadeTransition())
                        }
                    ),
                    contentDescription = null
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(Size.containerPadding, 0.dp, 0.dp, 0.dp)
                        .weight(1F, true)
                        .height(Size.middleAvater)
                        .background(Color.colorBg, RoundedCornerShape(Size.middleAvater))
                ) {
                    Text(
                        modifier = Modifier
                            .padding(Size.containerPadding,0.dp,0.dp,0.dp),
                        text = "长津湖之水门桥",
                        style = TextStyle(color = Color.disableColor),
                    )
                }

            }
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

            Row (
                modifier = Style.boxDecoration
                    ){
                Column(
                    modifier = Modifier.weight(1F, true),
                    horizontalAlignment =  Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.mipmap.icon_hot),
                        modifier = Modifier
                            .size(Size.bigIcon)
                            .padding(0.dp, 0.dp, 0.dp, Size.smallMargin),
                        contentDescription = ""
                    )
                    Text(text = "热门")
                }
                Column(
                    modifier = Modifier.weight(1F, true),
                    horizontalAlignment =  Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.mipmap.icon_play),
                        modifier = Modifier
                            .size(Size.bigIcon)
                            .padding(0.dp, 0.dp, 0.dp, Size.smallMargin),
                        contentDescription = ""
                    )
                    Text(text = "预告")
                }
                Column(
                    modifier = Modifier.weight(1F, true),
                    horizontalAlignment =  Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.mipmap.icon_top),
                        modifier = Modifier
                            .size(Size.bigIcon)
                            .padding(0.dp, 0.dp, 0.dp, Size.smallMargin),
                        contentDescription = ""
                    )
                    Text(text = "最新")
                }
                Column(
                    modifier = Modifier.weight(1F, true),
                    horizontalAlignment =  Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.mipmap.icon_classify),
                        modifier = Modifier
                            .size(Size.bigIcon)
                            .padding(0.dp, 0.dp, 0.dp, Size.smallMargin),
                        contentDescription = ""
                    )
                    Text(text = "分类")
                }
            }
            Divider(
                color = Color.Transparent,
                modifier = Modifier
                    .height(Size.containerPadding)
                    .fillMaxWidth())

        }
    }
}