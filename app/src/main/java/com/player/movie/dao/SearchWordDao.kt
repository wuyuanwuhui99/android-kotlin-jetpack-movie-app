package com.player.movie.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.player.movie.entity.SearchWordEntity

@Dao
interface SearchWordDao {
    @Insert
    fun insert(vararg searchWordEntity: SearchWordEntity?)

    @Delete
    fun delete(vararg searchWordEntity: SearchWordEntity?)

    @Update
    fun update(vararg searchWordEntity: SearchWordEntity?): Int

    @Query("SELECT * FROM search_word")
    fun query(): List<SearchWordEntity?>?
}