package com.player

import android.app.Application
import com.player.movie.database.AppSqliteDataBase
import com.player.movie.database.MovieSearchHistoryDatabase
import com.player.movie.entity.UserEntity
import com.player.utils.SharedPreferencesUtils


class BaseApplication : Application() {

    var token: String = ""
    var userEntity: UserEntity = UserEntity()

    override fun onCreate() {
        super.onCreate()
        mApp = this
        SHDB = MovieSearchHistoryDatabase.init(context = this)
        token = SharedPreferencesUtils.getParam(this, "token", "") as String
    }

    companion object {
        fun getInstance(): BaseApplication {
            return mApp
        }
        lateinit var SHDB: AppSqliteDataBase
        lateinit var mApp: BaseApplication
    }
}
