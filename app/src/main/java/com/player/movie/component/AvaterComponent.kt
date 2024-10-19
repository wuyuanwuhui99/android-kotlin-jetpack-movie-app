package com.player.movie.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import coil.transition.CrossfadeTransition
import com.player.constant.Constant
import com.player.model.UserViewModel
import com.player.R

@Composable
fun AvaterComponent (
    userViewModel:UserViewModel,
    size: Dp,
    navController: NavHostController?,
){
    Image(
        modifier = Modifier
            .size(size, size)
            .clip(RoundedCornerShape(size))
            .clickable {
                navController?.navigate("UserScreen")
            }
        ,
        painter = if(userViewModel.avater.value != "") {
            rememberImagePainter(
                data = Constant.HOST + userViewModel.avater.value,
                builder = {
                    transition(CrossfadeTransition())
                })
        }else{
            painterResource(id = R.mipmap.default_avater)
        },

        contentDescription = null
    )
}