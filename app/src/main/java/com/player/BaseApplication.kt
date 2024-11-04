package com.player

import android.app.Application
import com.player.movie.database.SearchHistoryDatabase
import com.player.movie.entity.UserEntity
import com.player.utils.SharedPreferencesUtils


class BaseApplication : Application() {

    var token: String = ""
    var userEntity: UserEntity = UserEntity()

    override fun onCreate() {
        super.onCreate()
        mApp = this
        sdDb = SearchHistoryDatabase.getInstance(this)!!
        token = SharedPreferencesUtils.getParam(this, "token", "") as String
    }

    companion object {
        fun getInstance(): BaseApplication {
            return mApp
        }
        lateinit var sdDb: SearchHistoryDatabase
        lateinit var mApp: BaseApplication
    }
}
