package com.player.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

private val DarkColorPalette = darkColors(
    primary = ThemeColor.Purple200,
    primaryVariant = ThemeColor.Purple700,
    secondary = ThemeColor.Teal200,
    background = ThemeColor.colorBgDark
)

private val LightColorPalette = lightColors(
    primary = ThemeColor.Purple500,
    primaryVariant = ThemeColor.Purple700,
    secondary = ThemeColor.Teal200,
    background = ThemeColor.colorBg
)

object ThemeStyle {
    val boxDecoration: Modifier = Modifier
        .fillMaxWidth()
        .background(ThemeColor.colorWhite,RoundedCornerShape(ThemeSize.middleRadius))
        .padding(ThemeSize.containerPadding)
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