package com.player.movie.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.alibaba.fastjson.JSON
import com.player.http.RequestUtils
import com.player.http.ResultEntity
import com.player.model.UserViewModel
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
        var keyword = remember { mutableStateOf("") }
        AvaterComponent(userViewModel,Size.middleAvater)
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
                        keyword.value = movieEntity.movieName
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