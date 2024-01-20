package com.movie.mymovie.movie.entity

import java.util.*

class MovieEntity {
    var id: Long? = null//主键
    var movieId: Long? = null
    var director: String? = null//导演
    var star: String? = null//主演
    var type: String? = null//类型
    var countryLanguage: String? = null//国家/语言
    var viewingState: String? = null//观看状态
    var releaseTime: String? = null//上映时间
    var plot: String? = null//剧情
    var updateTime: Date? = null //更新时间
    var movieName: String? = null//电影名称
    var isRecommend: String? = null //是否推荐，0:不推荐，1:推荐
    var img: String? = null //电影海报
    var classify: String? = null//分类 电影,电视剧,动漫,综艺,新片库,福利,午夜,恐怖,其他
    var sourceName: String? = null //来源名称，本地，骑士影院，爱奇艺
    var sourceUrl: String? = null //来源地址
    var createTime: Date? = null //创建时间
    var localImg: String? = null//本地图片
    var label: String? = null //播放集数
    var originalHref: String? = null //源地址
    var description: String? = null //简单描述
    var targetHref: String? = null //链接地址
    var useStatus: String? = null //0代表未使用，1表示正在使用，是banner和carousel图的才有
    var score: Double? = null //评分
    var category: String? = null //类目，值为banner首屏，carousel：滚动轮播
    var ranks: String? = null //排名
    var userId: String? = null//用户名，这这个表不需要，为了跟记录叫和收藏表的结构一致',
    var doubanUrl: String? = null
}