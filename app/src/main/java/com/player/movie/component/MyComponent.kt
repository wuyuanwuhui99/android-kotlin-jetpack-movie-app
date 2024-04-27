package com.player.movie.pages

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
import com.player.theme.Color
import com.player.theme.MymovieTheme
import com.player.theme.Size
import com.player.theme.Style
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun MyPage(userViewModel: UserViewModel) {
    val mainTextStyle = TextStyle(fontWeight = FontWeight.Bold, fontSize = Size.bigFontSize)
    val subTextStyle = TextStyle(color = Color.disableColor)
    MymovieTheme {
        LazyColumn(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = Size.containerPadding, end = Size.containerPadding)
                .scrollable(
                    state = rememberScrollState(0),
                    orientation = Orientation.Vertical
                )
        ) {
            item {
                Spacer(modifier = Modifier.height(Size.containerPadding))
                Row(
                    modifier = Style.boxDecoration,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AvaterComponent(userViewModel, Size.bigAvater)
                    Spacer(modifier = Modifier.width(Size.containerPadding))
                    Column(modifier = Modifier.weight(1F, true)) {
                        Text(text = userViewModel.username.value, style = mainTextStyle)
                        if ("" == userViewModel.sign.value) {
                            Text(text = userViewModel.sign.value)
                        }
                    }
                    Image(
                        painter = painterResource(id = R.mipmap.icon_edit),
                        modifier = Modifier
                            .size(Size.bigIcon),
                        contentDescription = ""
                    )
                }
            }
            item {
                val userMsgEntity by remember { mutableStateOf(UserMsgEntity()) }
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
                                userMsgEntity.setData(mUserMsgEntity);
                            }

                            override fun onFailure(call: Call<ResultEntity>, t: Throwable) {
                                println("错误")
                            }
                        })
                }
                Spacer(modifier = Modifier.height(Size.containerPadding))
                Row(
                    modifier = Style.boxDecoration,
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
            item {
                var isFold by remember { mutableStateOf(false) }
                Spacer(modifier = Modifier.height(Size.containerPadding))
                Column(modifier = Style.boxDecoration) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.pointerInput(Unit) {
                            detectTapGestures(
                                // 点击事件
                                onTap = {
                                    isFold = !isFold
                                }
                            )
                        }
                    ) {
                        Image(
                            modifier = Modifier
                                .size(Size.middleIcon, Size.middleIcon),
                            painter = painterResource(id = R.mipmap.icon_play_record),
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(Size.containerPadding))
                        Text(
                            text = stringResource(id = R.string.user_play_record_count),
                            modifier = Modifier.weight(1f)
                        )
                        Image(
                            modifier = Modifier
                                .size(Size.smallIcon, Size.smallIcon)
                                .graphicsLayer(
                                    rotationZ = if (isFold) {
                                        0f
                                    } else {
                                        90f
                                    }
                                ),// 应用旋转变换,
                            painter = painterResource(id = R.mipmap.icon_arrow),
                            contentDescription = null,
                        )
                    }
                    if(!isFold){
                        Spacer(modifier = Modifier.height(Size.containerPadding))
                        Divider(modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.colorBg)
                            .height(1.dp))
                        val playRecordMovieList = remember { mutableStateListOf<MovieEntity>() }
                        LaunchedEffect(Unit) {
                            RequestUtils.movieInstance.getPlayRecord()
                                .enqueue(object : Callback<ResultEntity> {
                                    override fun onResponse(
                                        call: Call<ResultEntity>,
                                        response: Response<ResultEntity>
                                    ) {
                                        val movieList = JSON.parseArray(
                                            JSON.toJSONString(response.body()?.data ?: ""),
                                            MovieEntity::class.java
                                        )
                                        playRecordMovieList.addAll(movieList)
                                    }

                                    override fun onFailure(call: Call<ResultEntity>, t: Throwable) {
                                        println("错误")
                                    }
                                })
                        }
                        if(playRecordMovieList.size == 0){
                            Text(style = TextStyle(color = Color.disableColor, textAlign = TextAlign.Center),modifier = Modifier
                                .padding(Size.containerPadding).fillMaxWidth(),
                                text = stringResource(id = R.string.no_data))
                        }else {
                            LazyRow(modifier = Modifier.fillMaxWidth()) {
                                items(playRecordMovieList.size) { index ->
                                    // 这里可以是你的自定义组件，这里只是简单地显示文本
                                    Image(
                                        contentScale = ContentScale.FillHeight,
                                        modifier = Modifier
                                            .width(Size.movieWidth)
                                            .height(Size.movieWidth)
                                            .clip(RoundedCornerShape(Size.middleRadius)),
                                        painter = rememberImagePainter(data = if (playRecordMovieList[index].img == "") {
                                            playRecordMovieList[index].img
                                        } else {
                                            Constant.HOST + userViewModel.avater.value
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
        }
    }
}
