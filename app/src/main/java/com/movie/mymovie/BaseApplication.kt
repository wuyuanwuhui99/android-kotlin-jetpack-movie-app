package com.movie.mymovie

import android.app.Application
import com.movie.mymovie.movie.entity.UserEntity
import com.movie.mymovie.utils.SharedPreferencesUtils


class BaseApplication : Application() {

    var token: String = ""
    var userEntity: UserEntity = UserEntity()

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
