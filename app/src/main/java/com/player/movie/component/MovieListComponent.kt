package com.player.movie.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import coil.compose.rememberImagePainter
import coil.transition.CrossfadeTransition
import com.player.constant.Constant
import com.player.movie.entity.MovieEntity
import com.player.theme.Size

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MovieListComponent (
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
                            data = Constant.HOST + movieEntity.localImg,
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
                            data = Constant.HOST + movieList[it].localImg,
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