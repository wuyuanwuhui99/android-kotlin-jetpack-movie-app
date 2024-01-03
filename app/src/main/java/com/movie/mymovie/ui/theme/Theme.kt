package com.movie.mymovie.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

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