package com.player.movie.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.player.theme.ThemeColor
import com.player.theme.ThemeSize

@Composable
fun TitleComponent (
    title:String
){
    Row (verticalAlignment= Alignment.CenterVertically){
        Divider(modifier = Modifier
            .height(ThemeSize.containerPadding)
            .width(ThemeSize.lineWidth)
            .background(ThemeColor.activeColor)
        )
        Text(text = title,modifier = Modifier.padding(start = ThemeSize.smallMargin))
    }
}