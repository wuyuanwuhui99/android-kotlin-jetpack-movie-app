package com.player.movie.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.text.TextStyle
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

var title:String = ""
var mValue:String =  ""
var field:String = ""

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
                val showDialog = remember{ mutableStateOf(false)}

                if(showDialog.value){
                    MyDialog(showDialog,"修改$title"){
                        Row(verticalAlignment=Alignment.CenterVertically,modifier = Modifier.padding(start=ThemeSize.containerPadding, end = ThemeSize.containerPadding)) {
                            Text(text = title)
                            Spacer(modifier = Modifier.width(ThemeSize.containerPadding))
                            TextField(
                                value = mValue,
                                colors = TextFieldDefaults.textFieldColors(
                                    backgroundColor = ThemeColor.transparent,
                                    focusedIndicatorColor=ThemeColor.transparent
                                ),
                                modifier = Modifier
                                    .clip(RoundedCornerShape(ThemeSize.middleRadius))
                                    .background(ThemeColor.disableColor)
                                    .height(ThemeSize.inputHeight)
                                    .border(
                                        ThemeSize.borderWidth,
                                        ThemeColor.borderColor,
                                        RoundedCornerShape(ThemeSize.middleRadius)
                                    )
                                    .weight(1f),
                                onValueChange = {
                                    mValue = it
                                }
                            )
                        }
                    }
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
            title = name
            mValue = value
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
fun MyDialog(showDialg:MutableState<Boolean>,title:String,content: @Composable () -> Unit){
    Dialog(
        onDismissRequest = {
            showDialg.value = false
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
            Text(text = title,
                modifier=Modifier.fillMaxWidth(),
                fontSize = ThemeSize.middleFontSize,
                fontWeight = FontWeight.Bold,
                textAlign= TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(ThemeSize.containerPadding))
            content()
            Spacer(modifier = Modifier.height(ThemeSize.containerPadding))
            Divider(modifier = Modifier
                .fillMaxWidth()
                .background(ThemeColor.colorBg)
                .height(ThemeSize.borderWidth))
            Row {
                Button(
                    onClick = {
                        showDialg.value = false
                    },
                    modifier = Modifier
                        .weight(1f, true)
                        .height(ThemeSize.buttonHeight),
                    shape = RoundedCornerShape(ThemeSize.middleRadius),
                    colors = ButtonDefaults.buttonColors(backgroundColor = ThemeColor.colorWhite),
                ) {
                    Text(text = "取消", style = TextStyle(color = ThemeColor.subTitle))
                }
                Divider(modifier = Modifier
                    .height(ThemeSize.buttonHeight)
                    .background(ThemeColor.colorBg)
                    .width(ThemeSize.borderWidth))
                Button(
                    onClick = {
                        showDialg.value = false
                    },
                    modifier = Modifier
                        .weight(1f, true)
                        .height(ThemeSize.buttonHeight),
                    shape = RoundedCornerShape(ThemeSize.middleRadius),
                    colors = ButtonDefaults.buttonColors(backgroundColor = ThemeColor.colorWhite),
                ) {
                    Text(text = "确定")
                }
            }
        }
    }
}