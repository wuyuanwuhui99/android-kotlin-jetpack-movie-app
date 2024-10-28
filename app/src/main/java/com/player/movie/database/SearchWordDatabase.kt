package com.player.movie.database

import androidx.compose.ui.platform.LocalContext
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.player.movie.dao.SearchWordDao
import com.player.movie.entity.SearchWordEntity
import com.player.utils.Converters
import kotlinx.coroutines.CoroutineScope

/**
 * @author: wuwenqiang
 * @description: 生成实体类和表结构
 * @date: 2022-08-10 21:02
 */
@Database(entities = [SearchWordEntity::class], version = 1, exportSchema = false)
@TypeConverters(
    Converters::class
)
abstract class SearchWordDatabase : RoomDatabase() {
    // 获取该数据库中某张表的持久化对象
    abstract fun searchWordDao(): SearchWordDao?

    companion object {
        // 单例
        private var database: SearchWordDatabase? = null
        @Synchronized
        fun getInstance(context: CoroutineScope): SearchWordDatabase? {
            if (database == null) {
                database = Room.databaseBuilder(
                    LocalContext.current,
                    SearchWordDatabase::class.java,
                    "search.db"
                ).build()
            }
            return database
        }
    }
}