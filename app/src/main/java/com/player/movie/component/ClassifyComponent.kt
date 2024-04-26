package com.player.movie.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.player.R
import com.player.theme.Size
import com.player.theme.Style

@Composable
fun ClassifyComponent (){
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
}