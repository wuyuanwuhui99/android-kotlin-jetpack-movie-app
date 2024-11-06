package com.player.movie.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.player.movie.entity.MovieSearchHistoryEntity

@Dao
interface SearchHistoryDao {
    @Query("select * from movie_search_history order by create_time desc")
    fun getAllHistory():MutableList<MovieSearchHistoryEntity>

    @Insert
    suspend fun insertHistory(history: MovieSearchHistoryEntity):Long

    @Query("delete from movie_search_history")
    suspend fun clearAll():Int

    @Query("delete from movie_search_history where id=:id")
    suspend fun deleteById(id:Long):Int

    @Update
    suspend fun update(searchHistory: MovieSearchHistoryEntity)
}