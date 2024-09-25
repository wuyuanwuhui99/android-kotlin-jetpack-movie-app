package com.player.movie.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.player.R
import com.player.model.UserViewModel
import com.player.movie.component.AvaterComponent
import com.player.theme.MymovieTheme
import com.player.theme.ThemeColor
import com.player.theme.ThemeSize
import com.player.theme.ThemeStyle

@Composable
fun UserScreen( navController: NavHostController,userViewModel: UserViewModel) {
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
                Column(
                    modifier = ThemeStyle.boxDecoration,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "头像",modifier = Modifier.weight(1f))
                        AvaterComponent(size = ThemeSize.bigIcon,userViewModel=userViewModel)
                        Spacer(modifier = Modifier.width(ThemeSize.containerPadding))
                        Image(
                            painter = painterResource(id = R.mipmap.icon_arrow),
                            modifier = Modifier
                                .size(ThemeSize.smallIcon),
                            contentDescription = ""
                        )
                    }
                    DividerScreen()
                    UserInfoScreen("昵称",userViewModel.username.value)
                    DividerScreen()
                    UserInfoScreen("电话",userViewModel.telephone.value)
                    DividerScreen()
                    UserInfoScreen("邮箱",userViewModel.email.value)
                    DividerScreen()
                    UserInfoScreen("生日",userViewModel.birthday.value)
                    DividerScreen()
                    UserInfoScreen("性别",if(userViewModel.sex.value == 0) {"男"} else {"女"})
                    DividerScreen()
                    UserInfoScreen("个性签名",userViewModel.sign.value)
                    DividerScreen()
                    UserInfoScreen("区域",userViewModel.region.value)
                }
            }
        }
    }
}

/**
 * @desc 分割线
 * @date 2024-09-25 22:49
 * @author wuwenqiang
 */
@Composable
fun DividerScreen(){
    Spacer(modifier = Modifier.height(ThemeSize.containerPadding))
    Divider(
        color = ThemeColor.disableColor,
        modifier = Modifier
            .height(ThemeSize.borderWidth)
            .fillMaxWidth())
    Spacer(modifier = Modifier.height(ThemeSize.containerPadding))
}

@Composable
fun UserInfoScreen(name:String,value:String){
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(text = name,modifier = Modifier.weight(1f))
        Text(text = value)
        Spacer(modifier = Modifier.width(ThemeSize.containerPadding))
        Image(
            painter = painterResource(id = R.mipmap.icon_arrow),
            modifier = Modifier
                .size(ThemeSize.smallIcon),
            contentDescription = ""
        )
    }
}