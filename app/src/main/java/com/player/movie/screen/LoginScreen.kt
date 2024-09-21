package com.player.movie.screen

import android.content.Context
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
import com.player.theme.Color
import com.player.theme.MymovieTheme
import com.player.theme.Size
import com.player.theme.Style
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
                    .background(Color.colorBg)
                    .padding(Size.containerPadding)

            ) {
                Column(
                    modifier = Style.boxDecoration.fillMaxHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(Size.bigIcon))
                    Image(
                        painter = painterResource(id = R.mipmap.icon_logo),
                        modifier = Modifier
                            .size(Size.bigAvater),
                        contentDescription = ""
                    )
                    Spacer(modifier = Modifier.height(Size.bigIcon))
                    userId = viewModel.userId.value
                    TextField(
                        leadingIcon = {
                            Image(
                                painter = painterResource(id = R.mipmap.icon_user_active),
                                modifier = Modifier
                                    .size(Size.smallIcon),
                                contentDescription = ""
                            )
                        },
                        value = viewModel.userId.value,
                        onValueChange = {
                            onUserValueChange(it)
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.transparent,
                            focusedIndicatorColor=Color.transparent
                        ),
                        modifier = Modifier
                            .clip(RoundedCornerShape(Size.superRadius))
                            .background(Color.transparent)
                            .height(Size.inputHeight)
                            .border(
                                Size.borderWidth,
                                Color.borderColor,
                                RoundedCornerShape(Size.middleBtnHeight)
                            )
                            .fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(Size.containerPadding))
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
                                    .size(Size.smallIcon),
                                contentDescription = ""
                            )
                        },
                        onValueChange = {
                            onPasswordValueChange(it)
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.transparent,
                            focusedIndicatorColor=Color.transparent
                        ),
                        value = password,
                        modifier = Modifier
                            .clip(RoundedCornerShape(Size.superRadius))
                            .background(Color.transparent)
                            .height(Size.inputHeight)
                            .border(
                                Size.borderWidth,
                                Color.borderColor,
                                RoundedCornerShape(Size.middleBtnHeight)
                            )
                            .fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(Size.containerPadding))
                    Button(
                        shape = RoundedCornerShape(Size.superRadius),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.warnColor
                        ),
                        modifier = Modifier
                            .height(Size.inputHeight)
                            .fillMaxWidth(),
                        onClick = {
                            useLogin(navController,context,viewModel)
                        }) {
                        Text(text = "登录",style = TextStyle(color = Color.colorWhite))
                    }
                    Spacer(modifier = Modifier.height(Size.containerPadding))
                    Button(
                        shape = RoundedCornerShape(Size.superRadius),
                        border = BorderStroke(1.dp, Color.borderColor),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.transparent),
                        modifier = Modifier
                            .height(Size.inputHeight)
                            .fillMaxWidth(),
                        onClick = {

                        }) {
                        Text(text = "注册",style = TextStyle(color = Color.normalColor))
                    }
                }
            }
        }
    }
}

fun onUserValueChange(value: String) {
    userId = value
}

fun onPasswordValueChange(value: String) {
    password = value
}

fun useLogin(navController:NavHostController,context:Context,viewModel: UserViewModel) {
    if (userId == "") {

    }else if(password == ""){

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
                }
            }

            override fun onFailure(call: Call<ResultEntity>, t: Throwable) {
                System.out.println(t)
            }
        })
    }
}