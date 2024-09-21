package com.player.movie.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import com.google.gson.Gson
import com.player.BaseApplication
import com.player.constant.Constant
import com.player.http.RequestUtils
import com.player.http.ResultEntity
import com.player.model.UserViewModel
import com.player.movie.entity.UserEntity
import com.player.theme.MymovieTheme
import com.player.utils.SharedPreferencesUtils
import kotlinx.coroutines.delay
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@Composable
fun LaunchScreen(navController: NavHostController,viewModel: UserViewModel){
    MymovieTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            Scaffold(modifier = Modifier.fillMaxSize()){
                Text(text = "欢迎使用",textAlign = TextAlign.Center,//文字居中对齐
                    modifier = Modifier.fillMaxSize().wrapContentHeight(Alignment.CenterVertically)
                )
                val context = LocalContext.current
                LaunchedEffect(Unit) {
                    delay(2000) // 延时2000毫秒(2秒)
                    val token:String = SharedPreferencesUtils.getParam(context,Constant.TOKEN,"") as String
                    if(token == ""){
                        navController.navigate("LoginScreen")
                    }else{
                        BaseApplication.getInstance().token = token
                        val userData: Call<ResultEntity> = RequestUtils.movieInstance.userData
                        userData.enqueue(object : Callback<ResultEntity> {
                            override fun onResponse(call: Call<ResultEntity>, response: Response<ResultEntity>) {
                                val gson = Gson()
                                val body: ResultEntity? = response.body()
                                if (body?.token != null) {
                                    BaseApplication.getInstance().token = body.token
                                    val userEntity = gson.fromJson(
                                        gson.toJson(body.data),
                                        UserEntity::class.java
                                    )
                                    BaseApplication.getInstance().userEntity = userEntity
                                    viewModel.setUserEntity(userEntity)
                                    SharedPreferencesUtils.setParam(context,Constant.TOKEN,body.token)
                                    navController.navigate("MainScreen")
                                }else{
                                    navController.navigate("LoginScreen")
                                }
                            }

                            override fun onFailure(call: Call<ResultEntity>, t: Throwable) {
                                navController.navigate("LoginScreen")
                            }
                        })
                    }
                }
            }
        }
    }
}