package com.movie.mymovie.movie.component

import android.widget.ScrollView
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import coil.compose.rememberImagePainter
import coil.transition.CrossfadeTransition
import com.alibaba.fastjson.JSON
import com.movie.mymovie.BaseApplication
import com.movie.mymovie.http.RequestUtils
import com.movie.mymovie.http.ResultEntity
import com.movie.mymovie.movie.api.Api
import com.movie.mymovie.movie.entity.MovieEntity
import com.movie.mymovie.ui.theme.Size
import com.movie.mymovie.ui.theme.Style
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CategoryComponent (
    movieList:Array<MovieEntity>,
    direction:String = "horizontal"
){
    if(direction.equals("horizontal")){
        Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
            for (movieEntity in movieList){
                Column(modifier = Modifier.width(Size.movieWidth)) {
                    Image(
                        modifier = Modifier
                            .width(Size.movieWidth)
                            .height(Size.movieHeight)
                            .clip(RoundedCornerShape(Size.middleRadius)),
                        painter = rememberImagePainter(
                            data = Api.HOST + movieEntity.localImg,
                            builder = {
                                transition(CrossfadeTransition())
                            }
                        ),
                        contentDescription = null
                    )
                }
            }
        }
    }else{
        LazyVerticalGrid(
            cells = GridCells.Fixed(3),
            contentPadding = PaddingValues(Size.smallMargin)
        ){
            items(movieList.size) {
                Column() {
                    Image(
                        modifier = Modifier
                            .width(Size.movieWidth)
                            .height(Size.movieHeight)
                            .clip(RoundedCornerShape(Size.middleRadius)),
                        painter = rememberImagePainter(
                            data = Api.HOST + movieList[it].localImg,
                            builder = {
                                transition(CrossfadeTransition())
                            }
                        ),
                        contentDescription = null
                    )
                }
            }
        }
    }

}