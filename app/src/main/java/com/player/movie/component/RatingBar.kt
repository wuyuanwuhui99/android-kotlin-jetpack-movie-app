package com.player.movie.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import com.player.R
import com.player.theme.ThemeSize
import kotlin.math.floor
import kotlin.math.round

@Composable
fun RatingBar(
    rating: Float,
    size:Dp= ThemeSize.smallIcon,
    spaceBetween: Dp = ThemeSize.smallMargin
) {

    Row {
        val solidStart: Int = floor(rating / 2).toInt()
        val halfStar: Int = round(rating % 2).toInt()
        val emptyStar:Int = 5 - solidStart - halfStar
        repeat(solidStart){// 实心星星
            Image(
                modifier = Modifier
                    .size(size, size),
                painter = painterResource(id = R.mipmap.icon_full_star),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(spaceBetween))
        }
        repeat(halfStar){// 半个星星
            Image(
                modifier = Modifier
                    .size(size, size),
                painter = painterResource(id = R.mipmap.icon_half_star),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(spaceBetween))
        }
        repeat(emptyStar){// 空心星星
            Image(
                modifier = Modifier
                    .size(size, size),
                painter = painterResource(id = R.mipmap.icon_empty_star),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(spaceBetween))
        }
    }
}

fun getStart(rating:Float){

}
