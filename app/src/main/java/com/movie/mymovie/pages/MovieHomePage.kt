package com.movie.mymovie.pages

import android.widget.ImageView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.transition.CrossfadeTransition
import com.movie.mymovie.BaseApplication
import com.movie.mymovie.api.Api
import com.movie.mymovie.ui.theme.Color
import com.movie.mymovie.ui.theme.MymovieTheme
import com.movie.mymovie.ui.theme.Size
import com.movie.mymovie.ui.theme.Style

@Composable
fun MovieHomePage() {
    MymovieTheme {
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxWidth()
                .padding(Size.containerPadding, 0.dp, Size.containerPadding, Size.containerPadding),
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
                        .padding(Size.containerPadding,0.dp,0.dp,0.dp)
                        .weight(1F, true)
                        .height(Size.middleAvater)
                        .background(Color.colorBg,RoundedCornerShape(Size.middleAvater))
                ) {
                    Text(
                        modifier = Modifier
                            .padding(Size.containerPadding,0.dp,0.dp,0.dp),
                        text = "长津湖之水门桥",
                        style = TextStyle(color = Color.disableColor),
                    )
                }

            }
        }
    }
}