package com.movie.mymovie

import android.app.Application
import com.movie.mymovie.entity.UserEntity
import com.movie.mymovie.utils.SharedPreferencesUtils


class BaseApplication : Application() {
    var token: String = "";
    var userEntity: UserEntity? = null

    override fun onCreate() {
        super.onCreate()
        instance = this
        token = SharedPreferencesUtils.getParam(this, "token", "") as String
    }

    companion object {
        lateinit var instance: BaseApplication
    }
}
