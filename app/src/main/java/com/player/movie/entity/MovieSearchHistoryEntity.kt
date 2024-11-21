package com.player.movie.entity

import androidx.annotation.Keep
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import androidx.room.Entity

@Keep
@Entity(tableName = "movie_search_history")
data class MovieSearchHistoryEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0L,
    @ColumnInfo(name = "movie_name")
    val movieName: String,
    @ColumnInfo(name = "create_time")
    val createTime: Long = 0L,
)