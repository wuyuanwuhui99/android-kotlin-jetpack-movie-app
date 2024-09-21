package com.player.movie.screen

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.gson.Gson
import com.player.BaseApplication
import com.player.R
import com.player.constant.Constant
import com.player.http.RequestUtils
import com.player.http.ResultEntity
import com.player.model.UserViewModel
import com.player.movie.entity.UserEntity
import com.player.theme.ThemeColor
import com.player.theme.MymovieTheme
import com.player.theme.ThemeSize
import com.player.theme.ThemeStyle
import com.player.utils.MD5
import com.player.utils.SharedPreferencesUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

var userId: String = ""
var password: String = ""

@Composable
fun LoginScreen(navController: NavHostController, viewModel: UserViewModel) {
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
                    modifier = ThemeStyle.boxDecoration.fillMaxHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(ThemeSize.bigIcon))
                    Image(
                        painter = painterResource(id = R.mipmap.icon_logo),
                        modifier = Modifier
                            .size(ThemeSize.bigAvater),
                        contentDescription = ""
                    )
                    Spacer(modifier = Modifier.height(ThemeSize.bigIcon))
                    userId = viewModel.userId.value
                    TextField(
                        leadingIcon = {
                            Image(
                                painter = painterResource(id = R.mipmap.icon_user_active),
                                modifier = Modifier
                                    .size(ThemeSize.smallIcon),
                                contentDescription = ""
                            )
                        },
                        value = viewModel.userId.value,
                        onValueChange = {
                            userId = it
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = ThemeColor.transparent,
                            focusedIndicatorColor=ThemeColor.transparent
                        ),
                        modifier = Modifier
                            .clip(RoundedCornerShape(ThemeSize.superRadius))
                            .background(ThemeColor.transparent)
                            .height(ThemeSize.inputHeight)
                            .border(
                                ThemeSize.borderWidth,
                                ThemeColor.borderColor,
                                RoundedCornerShape(ThemeSize.middleBtnHeight)
                            )
                            .fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(ThemeSize.containerPadding))
                    val context = LocalContext.current
                    password = SharedPreferencesUtils.getParam(
                        context,
                        viewModel.userId.value,
                        "123456"
                    ).toString()
                    TextField(
                        leadingIcon = {
                            Image(
                                painter = painterResource(id = R.mipmap.icon_password),
                                modifier = Modifier
                                    .size(ThemeSize.smallIcon),
                                contentDescription = ""
                            )
                        },
                        onValueChange = {
                            password = it
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = ThemeColor.transparent,
                            focusedIndicatorColor=ThemeColor.transparent
                        ),
                        value = password,
                        modifier = Modifier
                            .clip(RoundedCornerShape(ThemeSize.superRadius))
                            .background(ThemeColor.transparent)
                            .height(ThemeSize.inputHeight)
                            .border(
                                ThemeSize.borderWidth,
                                ThemeColor.borderColor,
                                RoundedCornerShape(ThemeSize.middleBtnHeight)
                            )
                            .fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(ThemeSize.containerPadding))
                    Button(
                        shape = RoundedCornerShape(ThemeSize.superRadius),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = ThemeColor.warnColor
                        ),
                        modifier = Modifier
                            .height(ThemeSize.inputHeight)
                            .fillMaxWidth(),
                        onClick = {
                            useLogin(navController,context,viewModel)
                        }) {
                        Text(text = "登录",style = TextStyle(color = ThemeColor.colorWhite))
                    }
                    Spacer(modifier = Modifier.height(ThemeSize.containerPadding))
                    Button(
                        shape = RoundedCornerShape(ThemeSize.superRadius),
                        border = BorderStroke(1.dp, ThemeColor.borderColor),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = ThemeColor.transparent),
                        modifier = Modifier
                            .height(ThemeSize.inputHeight)
                            .fillMaxWidth(),
                        onClick = {

                        }) {
                        Text(text = "注册",style = TextStyle(color = ThemeColor.normalColor))
                    }
                }
            }
        }
    }
}

/**
 * 获取字符串对应的MD5
 * @date 2024-09-21 12:34
 * @return
 */
fun useLogin(navController:NavHostController,context:Context,viewModel: UserViewModel) {
    if (userId == "") {
        Toast.makeText(context,"账号不能为空", Toast.LENGTH_SHORT).show()
    }else if(password == ""){
        Toast.makeText(context,"密码不能为空", Toast.LENGTH_SHORT).show()
    }else{
        val userEntity = UserEntity()
        userEntity.userId = userId
        userEntity.password = MD5.getStrMD5(password)
        val login: Call<ResultEntity> = RequestUtils.movieInstance.login(userEntity)
        login.enqueue(object : Callback<ResultEntity> {
            override fun onResponse(call: Call<ResultEntity>, response: Response<ResultEntity>) {
                val gson = Gson()
                val body: ResultEntity? = response.body()
                if (body?.token != null) {
                    BaseApplication.getInstance().token = body.token
                    val userEntity1 = gson.fromJson(
                        gson.toJson(body.data),
                        UserEntity::class.java
                    )
                    BaseApplication.getInstance().userEntity = userEntity1
                    viewModel.setUserEntity(userEntity)
                    SharedPreferencesUtils.setParam(context, Constant.TOKEN,body.token)
                    navController.navigate("MainScreen")
                    Toast.makeText(context,"登录成功", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResultEntity>, t: Throwable) {

            }
        })
    }
}