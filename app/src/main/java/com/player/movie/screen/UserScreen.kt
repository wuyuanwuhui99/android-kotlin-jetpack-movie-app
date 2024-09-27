package com.player.movie.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.SecureFlagPolicy
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
                val  showDialog = remember{ mutableStateOf(false)}
                if(showDialog.value){
                    MyDialog()
                }
                Column(
                    modifier = ThemeStyle.boxDecoration,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "头像",modifier = Modifier.weight(1f))
                        AvaterComponent(size = ThemeSize.bigAvater,userViewModel=userViewModel)
                        Spacer(modifier = Modifier.width(ThemeSize.containerPadding))
                        Image(
                            painter = painterResource(id = R.mipmap.icon_arrow),
                            modifier = Modifier
                                .size(ThemeSize.smallIcon),
                            contentDescription = ""
                        )
                    }
                    DividerScreen()
                    UserInfoScreen("昵称",userViewModel.username.value,showDialog)
                    DividerScreen()
                    UserInfoScreen("电话",userViewModel.telephone.value,showDialog)
                    DividerScreen()
                    UserInfoScreen("邮箱",userViewModel.email.value,showDialog)
                    DividerScreen()
                    UserInfoScreen("生日",userViewModel.birthday.value,showDialog)
                    DividerScreen()
                    UserInfoScreen("性别",if(userViewModel.sex.value == 0) {"男"} else {"女"},showDialog)
                    DividerScreen()
                    UserInfoScreen("个性签名",userViewModel.sign.value,showDialog)
                    DividerScreen()
                    UserInfoScreen("区域",userViewModel.region.value,showDialog)
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
fun UserInfoScreen(name:String,value:String,showDialg:MutableState<Boolean>){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable {
            showDialg.value = true
        }
    ) {
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

@Composable
fun MyDialog(){
    val state = remember {
        mutableStateOf(true)
    }
    Dialog(
        onDismissRequest = {
            state.value = false
        },
        properties = DialogProperties(dismissOnBackPress=true,dismissOnClickOutside = true,securePolicy = SecureFlagPolicy.SecureOff)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = ThemeSize.containerPadding)
                .background(
                    ThemeColor.colorWhite, shape = RoundedCornerShape(ThemeSize.middleRadius)
                )
        ){
            Spacer(modifier = Modifier.height(ThemeSize.containerPadding))
            Text(text = "对话框标题",
                fontSize = ThemeSize.middleFontSize,
                fontWeight = FontWeight.Bold,
                textAlign= TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(ThemeSize.containerPadding))
            Text(text = "对话框内容,对话框内容,对话框内容,对话框内容,对话框内容,对话框内容")
            Spacer(modifier = Modifier.height(ThemeSize.containerPadding))
            Divider(modifier = Modifier
                .fillMaxWidth()
                .background(ThemeColor.colorBg)
                .height(ThemeSize.borderWidth))
            Row() {
                Button(
                    onClick = {
                        state.value = false
                    },
                    modifier = Modifier.weight(1f,true).height(ThemeSize.middleBtnHeight),
                    shape = RoundedCornerShape(ThemeSize.middleRadius),
                    colors = ButtonDefaults.buttonColors(backgroundColor = ThemeColor.colorWhite),
                ) {
                    Text(text = "取消")
                }
                Divider(modifier = Modifier
                    .height(ThemeSize.middleBtnHeight)
                    .background(ThemeColor.colorBg)
                    .width(ThemeSize.borderWidth))
                Button(
                    onClick = {
                        state.value = false
                    },
                    modifier = Modifier.weight(1f,true).height(ThemeSize.middleBtnHeight),
                    shape = RoundedCornerShape(ThemeSize.middleRadius),
                    colors = ButtonDefaults.buttonColors(backgroundColor = ThemeColor.colorWhite),
                ) {
                    Text(text = "确定")
                }
            }
        }
    }
}