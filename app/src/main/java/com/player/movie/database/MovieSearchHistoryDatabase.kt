package com.player.movie.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.player.movie.dao.SearchHistoryDao


@Database(
    version = 2,//数据库的版本
    entities = [
        SearchHistoryDao::class,
    ] // 和表相互映射的实体类
)
abstract class AppSqliteDataBase:RoomDatabase(){
    abstract fun searchHistoryDao():SearchHistoryDao
}

class MovieSearchHistoryDatabase{
    companion object{
        fun init(context: Context):AppSqliteDataBase{
            val databaseBuilder = Room.databaseBuilder(
                context,
               AppSqliteDataBase::class.java,
               "SearchDB"
            ).apply {
                fallbackToDestructiveMigration()
                addMigrations( // 数据库升级迁移
                    MIGRATION_1_2
                )
            }

            return databaseBuilder.build()
        }
    }
}

val MIGRATION_1_2 = object : Migration(1,2){
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `movie_search_history` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL)")
    }
}