package com.player.movie.component

import android.annotation.SuppressLint
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import coil.transition.CrossfadeTransition
import com.alibaba.fastjson.JSON
import com.player.BaseApplication
import com.player.constant.Constant
import com.player.http.RequestUtils
import com.player.http.ResultEntity
import com.player.model.UserViewModel
import com.player.movie.activity.MainActivity
import com.player.movie.entity.MovieEntity
import com.player.theme.Color
import com.player.theme.Size
import com.player.theme.Style
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
@Composable
fun SearchComponent (
    userViewModel:UserViewModel = viewModel(),
    classify:String
){

    Row(
        modifier = Style.boxDecoration
    ) {
        var keyword = remember { mutableStateOf<String>("") }
        Image(
            modifier = Modifier
                .size(Size.middleAvater, Size.middleAvater)
                .clip(RoundedCornerShape(Size.middleAvater)),
            painter = rememberImagePainter(
                data = Constant.HOST + userViewModel.avater.value,
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
            val getKeyWordService: Call<ResultEntity> = RequestUtils.movieInstance.getKeyWord(classify)
            LaunchedEffect(Unit){
                getKeyWordService.enqueue(object : Callback<ResultEntity> {
                    override fun onResponse(call: Call<ResultEntity>, response: Response<ResultEntity>) {
                        val movieEntity = JSON.parseObject(
                            JSON.toJSONString(response.body()?.data ?: ""),
                            MovieEntity::class.java
                        )
                        keyword.value = movieEntity.movieName.toString()
                    }

                    override fun onFailure(call: Call<ResultEntity>, t: Throwable) {
                        println("错误")
                    }
                })
            }
            Text(
                modifier = Modifier
                    .padding(Size.containerPadding,0.dp,0.dp,0.dp),
                text = keyword.value,
                style = TextStyle(color = Color.disableColor),
            )
        }

    }
}