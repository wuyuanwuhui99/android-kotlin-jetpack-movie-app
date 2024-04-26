package com.player.movie.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.player.theme.Color
import com.player.theme.Size

@Composable
fun TitleComponent (
    title:String
){
    Row (verticalAlignment= Alignment.CenterVertically){
        Divider(modifier = Modifier
            .height(Size.containerPadding)
            .width(Size.lineWidth)
            .background(Color.activeColor)
        )
        Text(text = title,modifier = Modifier.padding(start = Size.smallMargin))
    }
}