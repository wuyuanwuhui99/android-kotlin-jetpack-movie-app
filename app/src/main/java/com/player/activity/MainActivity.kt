package com.player.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.gson.Gson
import com.player.movie.entity.UserEntity
import com.player.http.RequestUtils
import com.player.http.ResultEntity
import com.player.theme.MymovieTheme
import com.player.utils.SharedPreferencesUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.player.BaseApplication
import com.player.model.UserViewModel
import com.player.router.NavHostApp

class MainActivity : ComponentActivity() {
    val isInit: MutableState<Boolean> = mutableStateOf(false)
    private val viewModel: UserViewModel by viewModels()
    @ExperimentalPagerApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getUserData()
        setContent {
            if(isInit.value){
                MymovieTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Scaffold { innerPadding ->
                            NavHostApp(innerPadding,viewModel)
                        }
                    }
                }
            }
        }
    }

    /**
     * @author: wuwenqiang
     * @description: 获取用户信息
     * @date: 2023-12-04 15:59
     */
    private fun getUserData() {
        val userData: Call<ResultEntity> = RequestUtils.movieInstance.userData
        userData.enqueue(object : Callback<ResultEntity> {
            override fun onResponse(call: Call<ResultEntity>, response: Response<ResultEntity>) {
                val gson = Gson()
                val body: ResultEntity? = response.body()
                if (body != null) {
                    BaseApplication.getInstance().token = body.token
                }
                if (body != null) {
                    val userEntity = gson.fromJson(
                        gson.toJson(body.data),
                        UserEntity::class.java
                    )
                    BaseApplication.getInstance().userEntity = userEntity

                    viewModel.setUserEntity(userEntity)
                    SharedPreferencesUtils.setParam(
                        this@MainActivity,
                        "token",
                        body.token
                    )
                }
                isInit.value = true;
            }

            override fun onFailure(call: Call<ResultEntity>, t: Throwable) {
                println(t.message)
            }
        })
    }
}