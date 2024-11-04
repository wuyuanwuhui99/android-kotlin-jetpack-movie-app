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
    @Query("select * from movie_search_history order by create_time desc limit 10")
    fun getAllHistory():List<MovieSearchHistoryEntity>

//    @Insert
//    suspend fun insertHistory(history: MovieSearchHistoryEntity):Long

//    @Delete("delete from movie_search_history")
//    suspend fun clearAll():Long

//    @Delete(entity = "delete from movie_search_history where id=:id")
//    suspend fun deleteById(id:Long):Long

    @Update
    suspend fun update(searchHistory: MovieSearchHistoryEntity)
}