package com.movie.mymovie.movie.entity

import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import java.util.*

@Entity(tableName = "search_word")
class SearchWordEntity {
    @PrimaryKey(autoGenerate = true)
    var id = 0

    @ColumnInfo(name = "movie_name")
    var movieName: String? = null

    @ColumnInfo(name = "classify")
    var classify: String? = null

    @ColumnInfo(name = "creat_time")
    var creatTime: Date? = null

    @Ignore
    constructor(id: Int, movieName: String?, classify: String?, creatTime: Date?) {
        this.id = id
        this.movieName = movieName
        this.classify = classify
        this.creatTime = creatTime
    }

    constructor() {}
}