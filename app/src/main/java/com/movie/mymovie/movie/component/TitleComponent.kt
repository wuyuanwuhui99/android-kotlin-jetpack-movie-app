package com.movie.mymovie.movie.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.movie.mymovie.ui.theme.Size

@Composable
fun TitleComponent (
    title:String
){
    Row {
        Divider(modifier = Modifier
            .height(Size.containerPadding)
            .fillMaxWidth()
        )
        Text(text = title,modifier = Modifier.padding(start = Size.smallMargin))
    }
}