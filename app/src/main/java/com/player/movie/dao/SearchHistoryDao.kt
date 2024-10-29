package com.player.movie.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.player.movie.entity.MovieSearchHistoryEntity

@Dao
interface SearchHistoryDao {
    @Query("select * from movie_search_history order by create_time desc limit 10")
    fun getAllHistory():MutableList<MovieSearchHistoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(history: MovieSearchHistoryEntity):Long

    @Query("delete from movie_search_history")
    suspend fun clearAll()

    @Query("delete from movie_search_history where id=:id")
    suspend fun deleteById(id:Long)

    @Transaction
    @Query("select * from movie_search_history where id=:id")
    suspend fun findSearchHistoryById(id:Long):MovieSearchHistoryEntity?

    @Update
    suspend fun update(searchHistory: MovieSearchHistoryEntity)
}