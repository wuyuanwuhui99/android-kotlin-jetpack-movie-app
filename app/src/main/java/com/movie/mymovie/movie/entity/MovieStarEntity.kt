package com.movie.mymovie.movie.entity

import java.util.*

class MovieStarEntity {
    var id: Long? = null//主键
    var starName: String? = null//演员名称
    var img: String? = null//演员图片地址
    var localImg: String? = null//演员本地本地图片
    var createTime: Date? = null //创建时间
    var updateTime: Date? = null //创建时间
    var movieId: String? = null//电影的id
    var role: String? = null//角色
    var href: String? = null//演员的豆瓣链接地址
    var works: String? = null//代表作
}