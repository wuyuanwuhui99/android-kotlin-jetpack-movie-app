package com.player.movie.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.player.movie.dao.SearchHistoryDao
import com.player.movie.entity.MovieSearchHistoryEntity
import com.player.utils.Converters

/**
 * @author: wuwenqiang
 * @description: 生成实体类和表结构
 * @date: 2022-08-10 21:02
 */
@Database(entities = [MovieSearchHistoryEntity::class], version = 1, exportSchema = false)
@TypeConverters(
    Converters::class
)
abstract class SearchHistoryDatabase : RoomDatabase() {
    // 获取该数据库中某张表的持久化对象
    abstract fun searchHistoryDao(): SearchHistoryDao?

    companion object {
        // 单例
        private var database: SearchHistoryDatabase? = null
        @Synchronized
        fun getInstance(context: Context): SearchHistoryDatabase? {
            if (database == null) {
                database = Room.databaseBuilder<SearchHistoryDatabase>(
                    context.applicationContext,
                    SearchHistoryDatabase::class.java,
                    "search.db"
                ).build()
            }
            return database
        }
    }
}