package com.movie.mymovie.movie.entity

class MovieUrlEntity {
    var id: Int = 0//主键
    var movieName: String? = null//电影名称
    var movieId: Long? = null //对应的电影的id
    var href: String? = null //源地址
    var label: String? = null //集数
    var createTime: String? = null //创建时间
    var updateTime: String? = null//播放地址
    var url: String? = null //播放地址
    var playGroup: Int = 0//播放分组，1, 2
}