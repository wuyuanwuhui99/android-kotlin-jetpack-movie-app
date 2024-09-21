package com.player.movie.screen

import com.player.movie.component.RatingBar
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import coil.transition.CrossfadeTransition
import com.player.R
import com.player.constant.Constant
import com.player.movie.entity.MovieEntity
import com.player.theme.ThemeSize
import com.player.theme.ThemeStyle


@Composable
fun MovieDetailScreen(navController: NavHostController, movieEntity: MovieEntity) {
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
            Spacer(modifier = Modifier.height(ThemeSize.containerPadding))
            Row(modifier = ThemeStyle.boxDecoration) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        modifier = Modifier
                            .size(ThemeSize.movieWidth, ThemeSize.movieHeight)
                            .clip(RoundedCornerShape(ThemeSize.middleRadius)),
                        painter = rememberImagePainter(
                            data = Constant.HOST + movieEntity.localImg,
                            builder = {
                                transition(CrossfadeTransition())
                            }
                        ),
                        contentDescription = null
                    )
                    Image(
                        modifier = Modifier
                            .size(ThemeSize.middleIcon, ThemeSize.middleIcon),
                        painter = painterResource(id = R.mipmap.icon_detail_play),
                        contentDescription = null
                    )
                }
                Spacer(modifier = Modifier.width(ThemeSize.containerPadding))
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = movieEntity.movieName,
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = ThemeSize.bigFontSize
                        )
                    )
                    Spacer(modifier = Modifier.height(ThemeSize.smallMargin))
                    if (movieEntity.description != "") {
                        Text(
                            text = movieEntity.description.replace("\n", "").replace(" ", ""),
                            textAlign = TextAlign.Start
                        )
                        Spacer(modifier = Modifier.height(ThemeSize.smallMargin))
                    }
                    Text(text = movieEntity.star)
                    Spacer(modifier = Modifier.height(ThemeSize.smallMargin))

                    Text(text = movieEntity.type)
                    Spacer(modifier = Modifier.height(ThemeSize.smallMargin))
                    Row() {
                        RatingBar(
                            rating = movieEntity.score.toFloat(),
                        )
                        Text(text = movieEntity.score.toString())
                    }
                }
            }
        }
    }
}