package com.player.movie.repository

import com.player.movie.database.AppSqliteDataBase
import com.player.movie.entity.MovieSearchHistoryEntity

class SearchHistoryRepository(private val db: AppSqliteDataBase) {
    /**
     * 获取搜索列表
     */
    fun getSearchHistoryList(): MutableList<MovieSearchHistoryEntity> {
        return db.searchHistoryDao().getAllHistory()
    }

    /**
     * 新增搜索历史记录
     */
    suspend fun insertHistory(searchHistory: MovieSearchHistoryEntity) {
        val oldHistory = db
            .searchHistoryDao()
            .findSearchHistoryById(searchHistory.id)

        if (oldHistory != null) {
            db.searchHistoryDao().update(searchHistory)
        } else {
            db.searchHistoryDao().insertHistory(searchHistory)
        }
    }

    /**
     * 通过ID删除历史记录
     */
    suspend fun deleteById(id: Long) {
        val searchHistory = db.searchHistoryDao().findSearchHistoryById(id)
        if (searchHistory != null) {
            // 将删除的标志更新成1，表示已经删除
            db.searchHistoryDao().update(searchHistory)
        }
        db.searchHistoryDao().deleteById(id)
    }

    /**
     * 更新历史记录
     */
    suspend fun updateHistory(searchHistory: MovieSearchHistoryEntity) {
        db.searchHistoryDao().update(searchHistory)
    }

    /**
     * 清除历史记录
     */
    suspend fun clearAllHistory() {
        db.searchHistoryDao().clearAll()
    }
}