package com.player.movie.screen

import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import coil.transition.CrossfadeTransition
import com.alibaba.fastjson.JSON
import com.player.R
import com.player.constant.Constant
import com.player.http.RequestUtils
import com.player.http.ResultEntity
import com.player.model.UserViewModel
import com.player.movie.component.AvaterComponent
import com.player.movie.entity.MovieEntity
import com.player.movie.entity.UserMsgEntity
import com.player.theme.ThemeColor
import com.player.theme.MymovieTheme
import com.player.theme.ThemeSize
import com.player.theme.ThemeStyle
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun MyScreen(userViewModel: UserViewModel,navController: NavHostController,value:PaddingValues) {
    MymovieTheme {
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
                UserProfile(userViewModel, navController)
            }
            item {
                UserMsg()
            }
            item {
                PlayRecord()
            }
            item {
                FavoriteRecord()
            }
            item {
                ViewRecord()
            }
            item {
                Menu(value)
            }
        }
    }
}

/**
 * @desc 用户头像
 * @date 2024-09-21 12:34
 * @author wuwenqiang
 */
@Composable
fun UserProfile(userViewModel:UserViewModel,navController: NavHostController){
    Spacer(modifier = Modifier.height(ThemeSize.containerPadding))
    Row(
        modifier = ThemeStyle.boxDecoration.clickable {
            navController.navigate("UserScreen")
        },
        verticalAlignment = Alignment.CenterVertically
    ) {
        AvaterComponent(userViewModel, ThemeSize.bigAvater)
        Spacer(modifier = Modifier.width(ThemeSize.containerPadding))
        Column(modifier = Modifier.weight(1F, true)) {
            Text(text = userViewModel.username.value, style = mainTextStyle)
            if ("" == userViewModel.sign.value) {
                Text(text = userViewModel.sign.value)
            }
        }
        Image(
            painter = painterResource(id = R.mipmap.icon_edit),
            modifier = Modifier
                .size(ThemeSize.bigIcon)
            ,
            contentDescription = "",
        )
    }
}

val mainTextStyle = TextStyle(fontWeight = FontWeight.Bold, fontSize = ThemeSize.bigFontSize)
val subTextStyle = TextStyle(color = ThemeColor.disableColor)


/**
 * @desc 用户信息
 * @date 2024-09-24 23:31
 * @author wuwenqiang
 */
@Composable
fun UserMsg(){
   var userMsgEntity by remember { mutableStateOf(UserMsgEntity()) }
    LaunchedEffect(Unit) {
        RequestUtils.movieInstance.getUserMsg()
            .enqueue(object : Callback<ResultEntity> {
                override fun onResponse(
                    call: Call<ResultEntity>,
                    response: Response<ResultEntity>
                ) {
                    val mUserMsgEntity: UserMsgEntity = JSON.parseObject(
                        JSON.toJSONString(response.body()?.data),
                        UserMsgEntity::class.java
                    )
                    userMsgEntity = mUserMsgEntity
                }

                override fun onFailure(call: Call<ResultEntity>, t: Throwable) {
                    println("错误")
                }
            })
    }
    Spacer(modifier = Modifier.height(ThemeSize.containerPadding))
    Row(
        modifier = ThemeStyle.boxDecoration,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f, true),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = userMsgEntity.userAge, style = mainTextStyle)
            Text(text = stringResource(id = R.string.user_day), style = subTextStyle)
        }
        Column(
            modifier = Modifier.weight(1f, true),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = userMsgEntity.favoriteCount, style = mainTextStyle)
            Text(
                text = stringResource(id = R.string.user_favorite_count),
                style = subTextStyle
            )
        }
        Column(
            modifier = Modifier.weight(1f, true),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = userMsgEntity.playRecordCount, style = mainTextStyle)
            Text(
                text = stringResource(id = R.string.user_play_record_count),
                style = subTextStyle
            )
        }
        Column(
            modifier = Modifier.weight(1f, true),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = userMsgEntity.viewRecordCount, style = mainTextStyle)
            Text(
                text = stringResource(id = R.string.user_view_record_count),
                style = subTextStyle
            )
        }
    }
}

/**
 * @desc 播放记录
 * @date 2024-09-24 23:31
 * @author wuwenqiang
 */
@Composable
fun PlayRecord(){
    var isFoldRecord by remember { mutableStateOf(false) }
    Spacer(modifier = Modifier.height(ThemeSize.containerPadding))
    Column(modifier = ThemeStyle.boxDecoration) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.pointerInput(Unit) {
                detectTapGestures(
                    // 点击事件
                    onTap = {
                        isFoldRecord = !isFoldRecord
                    }
                )
            }
        ) {
            Image(
                modifier = Modifier
                    .size(ThemeSize.middleIcon, ThemeSize.middleIcon),
                painter = painterResource(id = R.mipmap.icon_play_record),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(ThemeSize.containerPadding))
            Text(
                text = stringResource(id = R.string.user_play_record_count),
                modifier = Modifier.weight(1f)
            )
            Image(
                modifier = Modifier
                    .size(ThemeSize.smallIcon, ThemeSize.smallIcon)
                    .graphicsLayer(
                        rotationZ = if (isFoldRecord) {
                            0f
                        } else {
                            90f
                        }
                    ),// 应用旋转变换,
                painter = painterResource(id = R.mipmap.icon_arrow),
                contentDescription = null,
            )
        }
        if(!isFoldRecord){
            Spacer(modifier = Modifier.height(ThemeSize.containerPadding))
            Divider(modifier = Modifier
                .fillMaxWidth()
                .background(ThemeColor.colorBg)
                .height(1.dp))
            val playRecordMovieList = remember { mutableStateListOf<MovieEntity>() }
            LaunchedEffect(Unit) {
                RequestUtils.movieInstance.getPlayRecord(1,20)
                    .enqueue(object : Callback<ResultEntity> {
                        override fun onResponse(
                            call: Call<ResultEntity>,
                            response: Response<ResultEntity>
                        ) {
                            val movieList = JSON.parseArray(
                                JSON.toJSONString(response.body()?.data ?: ""),
                                MovieEntity::class.java
                            )
                            if(movieList != null){
                                playRecordMovieList.addAll(movieList)
                            }
                        }

                        override fun onFailure(call: Call<ResultEntity>, t: Throwable) {
                            println("错误")
                        }
                    })
            }
            if(playRecordMovieList.size == 0){
                Text(style = TextStyle(color = ThemeColor.disableColor, textAlign = TextAlign.Center),modifier = Modifier
                    .padding(ThemeSize.containerPadding)
                    .fillMaxWidth(),
                    text = stringResource(id = R.string.no_data))
            }else {
                LazyRow(modifier = Modifier.fillMaxWidth()) {
                    items(playRecordMovieList.size) { index ->
                        // 这里可以是你的自定义组件，这里只是简单地显示文本
                        Column( horizontalAlignment= Alignment.CenterHorizontally) {
                            Image(
                                contentScale = ContentScale.FillHeight,
                                modifier = Modifier
                                    .width(ThemeSize.movieWidth)
                                    .height(ThemeSize.movieWidth)
                                    .clip(RoundedCornerShape(ThemeSize.middleRadius)),
                                painter = rememberImagePainter(
                                    data = if (playRecordMovieList[index].img == "") { playRecordMovieList[index].img } else { Constant.HOST + playRecordMovieList[index].localImg},
                                    builder = {
                                        transition(CrossfadeTransition())
                                    }
                                ), contentDescription = null)
                            Spacer(modifier = Modifier.height(ThemeSize.smallMargin))
                            Text(text = playRecordMovieList[index].movieName)
                        }
                        if(index != playRecordMovieList.size - 1){
                            Spacer(modifier = Modifier.width(ThemeSize.containerPadding))
                        }
                    }
                }
            }
        }
    }
}

/**
 * @desc 收藏记录
 * @date 2024-09-24 23:31
 * @author wuwenqiang
 */
@Composable
fun FavoriteRecord(){
    var isFoldFavorite by remember { mutableStateOf(true) }
    Spacer(modifier = Modifier.height(ThemeSize.containerPadding))
    Column(modifier = ThemeStyle.boxDecoration) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.pointerInput(Unit) {
                detectTapGestures(
                    // 点击事件
                    onTap = {
                        isFoldFavorite = !isFoldFavorite
                    }
                )
            }
        ) {
            Image(
                modifier = Modifier
                    .size(ThemeSize.middleIcon, ThemeSize.middleIcon),
                painter = painterResource(id = R.mipmap.icon_collection),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(ThemeSize.containerPadding))
            Text(
                text = stringResource(id = R.string.user_favorite),
                modifier = Modifier.weight(1f)
            )
            Image(
                modifier = Modifier
                    .size(ThemeSize.smallIcon, ThemeSize.smallIcon)
                    .graphicsLayer(
                        rotationZ = if (isFoldFavorite) {
                            0f
                        } else {
                            90f
                        }
                    ),// 应用旋转变换,
                painter = painterResource(id = R.mipmap.icon_arrow),
                contentDescription = null,
            )
        }
        if(!isFoldFavorite){
            Spacer(modifier = Modifier.height(ThemeSize.containerPadding))
            Divider(modifier = Modifier
                .fillMaxWidth()
                .background(ThemeColor.colorBg)
                .height(1.dp))
            val favoriteMovieList = remember { mutableStateListOf<MovieEntity>() }
            LaunchedEffect(Unit) {
                RequestUtils.movieInstance.getFavoriteList(1,20)
                    .enqueue(object : Callback<ResultEntity> {
                        override fun onResponse(
                            call: Call<ResultEntity>,
                            response: Response<ResultEntity>
                        ) {
                            val movieList = JSON.parseArray(
                                JSON.toJSONString(response.body()?.data),
                                MovieEntity::class.java
                            )
                            favoriteMovieList.clear()
                            if(movieList != null){
                                favoriteMovieList.addAll(movieList)
                            }
                        }

                        override fun onFailure(call: Call<ResultEntity>, t: Throwable) {
                            println("错误")
                        }
                    })
            }
            if(favoriteMovieList.size == 0){
                Text(style = TextStyle(color = ThemeColor.disableColor, textAlign = TextAlign.Center),modifier = Modifier
                    .padding(ThemeSize.containerPadding)
                    .fillMaxWidth(),
                    text = stringResource(id = R.string.no_data))
            }else {
                LazyRow(modifier = Modifier.fillMaxWidth()) {
                    items(favoriteMovieList.size) { index ->
                        // 这里可以是你的自定义组件，这里只是简单地显示文本
                        Image(
                            contentScale = ContentScale.FillHeight,
                            modifier = Modifier
                                .width(ThemeSize.movieWidth)
                                .height(ThemeSize.movieHeight)
                                .clip(RoundedCornerShape(ThemeSize.middleRadius)),
                            painter = rememberImagePainter(data = if (favoriteMovieList[index].img == "") {
                                favoriteMovieList[index].img
                            } else {
                                Constant.HOST + favoriteMovieList[index].localImg
                            },
                                builder = {
                                    transition(CrossfadeTransition())
                                }
                            ), contentDescription = null)
                    }
                }
            }
        }
    }
}

/**
 * @desc 浏览记录
 * @date 2024-09-24 23:31
 * @author wuwenqiang
 */
@Composable
fun ViewRecord(){
    var isFoldView by remember { mutableStateOf(true) }
    Spacer(modifier = Modifier.height(ThemeSize.containerPadding))
    Column(modifier = ThemeStyle.boxDecoration) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.pointerInput(Unit) {
                detectTapGestures(
                    // 点击事件
                    onTap = {
                        isFoldView = !isFoldView
                    }
                )
            }
        ) {
            Image(
                modifier = Modifier
                    .size(ThemeSize.middleIcon, ThemeSize.middleIcon),
                painter = painterResource(id = R.mipmap.icon_record),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(ThemeSize.containerPadding))
            Text(
                text = stringResource(id = R.string.user_record),
                modifier = Modifier.weight(1f)
            )
            Image(
                modifier = Modifier
                    .size(ThemeSize.smallIcon, ThemeSize.smallIcon)
                    .graphicsLayer(
                        rotationZ = if (isFoldView) {
                            0f
                        } else {
                            90f
                        }
                    ),// 应用旋转变换,
                painter = painterResource(id = R.mipmap.icon_arrow),
                contentDescription = null,
            )
        }
        if(!isFoldView){
            Spacer(modifier = Modifier.height(ThemeSize.containerPadding))
            Divider(modifier = Modifier
                .fillMaxWidth()
                .background(ThemeColor.colorBg)
                .height(1.dp))
            val viewMovieList = remember { mutableStateListOf<MovieEntity>() }
            LaunchedEffect(Unit) {
                RequestUtils.movieInstance.getFavoriteList(1,20)
                    .enqueue(object : Callback<ResultEntity> {
                        override fun onResponse(
                            call: Call<ResultEntity>,
                            response: Response<ResultEntity>
                        ) {
                            val movieList = JSON.parseArray(
                                JSON.toJSONString(response.body()?.data),
                                MovieEntity::class.java
                            )
                            viewMovieList.clear()
                            if(movieList != null){
                                viewMovieList.addAll(movieList)
                            }
                        }

                        override fun onFailure(call: Call<ResultEntity>, t: Throwable) {
                            println("错误")
                        }
                    })
            }
            if(viewMovieList.size == 0){
                Text(style = TextStyle(color = ThemeColor.disableColor, textAlign = TextAlign.Center),modifier = Modifier
                    .padding(ThemeSize.containerPadding)
                    .fillMaxWidth(),
                    text = stringResource(id = R.string.no_data))
            }else {
                LazyRow(modifier = Modifier.fillMaxWidth()) {
                    items(viewMovieList.size) { index ->
                        // 这里可以是你的自定义组件，这里只是简单地显示文本
                        Image(
                            contentScale = ContentScale.FillHeight,
                            modifier = Modifier
                                .width(ThemeSize.movieWidth)
                                .height(ThemeSize.movieHeight)
                                .clip(RoundedCornerShape(ThemeSize.middleRadius)),
                            painter = rememberImagePainter(data = if (viewMovieList[index].img == "") {
                                viewMovieList[index].img
                            } else {
                                Constant.HOST + viewMovieList[index].localImg
                            },
                                builder = {
                                    transition(CrossfadeTransition())
                                }
                            ), contentDescription = null)
                    }
                }
            }
        }
    }
}

/**
 * @desc 菜单选项
 * @date 2024-09-24 23:31
 * @author wuwenqiang
 */
@Composable
fun Menu(value: PaddingValues){
    Spacer(modifier = Modifier.height(ThemeSize.containerPadding))
    Column(modifier = ThemeStyle.boxDecoration) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.pointerInput(Unit) {
                detectTapGestures(
                    // 点击事件
                    onTap = {

                    }
                )
            }
        ) {
            Image(
                modifier = Modifier
                    .size(ThemeSize.middleIcon, ThemeSize.middleIcon),
                painter = painterResource(id = R.mipmap.icon_music),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(ThemeSize.containerPadding))
            Text(
                text = stringResource(id = R.string.user_music),
                modifier = Modifier.weight(1f)
            )
            Image(
                modifier = Modifier
                    .size(ThemeSize.smallIcon, ThemeSize.smallIcon),// 应用旋转变换,
                painter = painterResource(id = R.mipmap.icon_arrow),
                contentDescription = null,
            )
        }
        Spacer(modifier = Modifier.height(ThemeSize.containerPadding))
        Divider(modifier = Modifier
            .fillMaxWidth()
            .background(ThemeColor.colorBg)
            .height(1.dp))
        Spacer(modifier = Modifier.height(ThemeSize.containerPadding))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.pointerInput(Unit) {
                detectTapGestures(
                    // 点击事件
                    onTap = {

                    }
                )
            }
        ) {
            Image(
                modifier = Modifier
                    .size(ThemeSize.middleIcon, ThemeSize.middleIcon),
                painter = painterResource(id = R.mipmap.icon_talk),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(ThemeSize.containerPadding))
            Text(
                text = stringResource(id = R.string.user_movie_talk),
                modifier = Modifier.weight(1f)
            )
            Image(
                modifier = Modifier
                    .size(ThemeSize.smallIcon, ThemeSize.smallIcon),// 应用旋转变换,
                painter = painterResource(id = R.mipmap.icon_arrow),
                contentDescription = null,
            )
        }
        Spacer(modifier = Modifier.height(ThemeSize.containerPadding))
        Divider(modifier = Modifier
            .fillMaxWidth()
            .background(ThemeColor.colorBg)
            .height(1.dp))
        Spacer(modifier = Modifier.height(ThemeSize.containerPadding))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.pointerInput(Unit) {
                detectTapGestures(
                    // 点击事件
                    onTap = {

                    }
                )
            }
        ) {
            Image(
                modifier = Modifier
                    .size(ThemeSize.middleIcon, ThemeSize.middleIcon),
                painter = painterResource(id = R.mipmap.icon_app),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(ThemeSize.containerPadding))
            Text(
                text = stringResource(id = R.string.user_applet),
                modifier = Modifier.weight(1f)
            )
            Image(
                modifier = Modifier
                    .size(ThemeSize.smallIcon, ThemeSize.smallIcon),// 应用旋转变换,
                painter = painterResource(id = R.mipmap.icon_arrow),
                contentDescription = null,
            )
        }
    }
    Spacer(modifier = Modifier
        .height(value.calculateBottomPadding() + ThemeSize.containerPadding))
}