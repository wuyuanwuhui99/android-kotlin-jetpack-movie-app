package com.player.movie.screen

import android.renderscript.ScriptGroup.Input
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

import androidx.navigation.NavHostController
import com.player.R
import com.player.model.UserViewModel
import com.player.theme.MymovieTheme
import com.player.theme.ThemeColor
import com.player.theme.ThemeSize
import com.player.theme.ThemeStyle

@Composable
fun MovieSearchScreen( navController: NavHostController,userViewModel: UserViewModel,keyword:String) {
    MymovieTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            Scaffold(
                modifier = Modifier
                    .fillMaxSize()
                    .background(ThemeColor.colorBg)
                    .padding(ThemeSize.containerPadding)

            ) {
                val inputValue = remember{ mutableStateOf(keyword) }
                SearchInput(inputValue)
            }
        }
    }
}

@Composable
fun SearchInput(inputValue: MutableState<String>){
    Row(
        modifier = ThemeStyle.boxDecoration
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .height(ThemeSize.middleAvater)
                .padding(ThemeSize.smallMargin, 0.dp, 0.dp, 0.dp)
                .background(ThemeColor.colorBg, RoundedCornerShape(ThemeSize.middleAvater))
        ) {
            TextField(
                value = inputValue.value,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = ThemeColor.transparent,
                    focusedIndicatorColor=ThemeColor.transparent
                ),
                modifier = Modifier
                    .clip(RoundedCornerShape(ThemeSize.middleRadius))
                    .height(ThemeSize.inputHeight)
                    .weight(1f)
                    .border(
                        ThemeSize.borderWidth,
                        ThemeColor.colorBg,
                    )
                    .weight(1f),
                onValueChange = {
                    inputValue.value = it
                }
            )
            if(inputValue.value != ""){
                Image(
                    painter = painterResource(id = R.mipmap.icon_clear),
                    modifier = Modifier
                        .size(ThemeSize.bigIcon)
                        .padding(0.dp, 0.dp, 0.dp, ThemeSize.smallMargin)
                        .clickable {
                            inputValue.value = ""
                        },
                    contentDescription = ""
                )
            }

        }

    }
}
