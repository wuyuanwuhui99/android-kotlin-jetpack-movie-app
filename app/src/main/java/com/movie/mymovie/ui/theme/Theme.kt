package com.movie.mymovie.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip

private val DarkColorPalette = darkColors(
    primary = Color.Purple200,
    primaryVariant = Color.Purple700,
    secondary = Color.Teal200,
    background = Color.colorBgDark
)

private val LightColorPalette = lightColors(
    primary = Color.Purple500,
    primaryVariant = Color.Purple700,
    secondary = Color.Teal200,
    background = Color.colorBg
)

object Style {
    val boxDecoration: Modifier = Modifier
        .fillMaxWidth()
        .background(Color.colorWhite,RoundedCornerShape(Size.middleRadius))
        .padding(Size.containerPadding)
}

@Composable
fun MymovieTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}