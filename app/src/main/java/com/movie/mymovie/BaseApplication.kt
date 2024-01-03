package com.movie.mymovie

import android.app.Application
import com.movie.mymovie.entity.UserEntity
import com.movie.mymovie.utils.SharedPreferencesUtils


class BaseApplication : Application() {

    var token: String = "";
    var userEntity: UserEntity? = null

    override fun onCreate() {
        super.onCreate()
        mApp = this
        token = SharedPreferencesUtils.getParam(this, "token", "") as String
    }

    companion object {
        fun getInstance(): BaseApplication {
            return mApp
        }
        lateinit var mApp: BaseApplication
    }
}
