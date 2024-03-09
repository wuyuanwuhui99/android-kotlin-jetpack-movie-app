package com.movie.mymovie.movie.entity

import java.util.*

class MovieEntity {
    var id: Long = 0//主键
    var movieId: Long=0
    var director: String = ""//导演
    var star: String = ""//主演
    var type: String = ""//类型
    var countryLanguage: String = ""//国家/语言
    var viewingState: String = ""//观看状态
    var releaseTime: String = ""//上映时间
    var plot: String = ""//剧情
    lateinit var updateTime: Date //更新时间
    var movieName: String = ""//电影名称
    var isRecommend: String = "" //是否推荐，0:不推荐，1:推荐
    var img: String = "" //电影海报
    var classify: String = ""//分类 电影,电视剧,动漫,综艺,新片库,福利,午夜,恐怖,其他
    var sourceName: String = "" //来源名称，本地，骑士影院，爱奇艺
    var sourceUrl: String = "" //来源地址
    lateinit var createTime: Date //创建时间
    var localImg: String = ""//本地图片
    var label: String = "" //播放集数
    var originalHref: String = "" //源地址
    var description: String = "" //简单描述
    var targetHref: String = "" //链接地址
    var useStatus: String = "" //0代表未使用，1表示正在使用，是banner和carousel图的才有
    var score: Double = 0.0 //评分
    var category: String = "" //类目，值为banner首屏，carousel：滚动轮播
    var ranks: String = "" //排名
    var userId: String = ""//用户名，这这个表不需要，为了跟记录叫和收藏表的结构一致',
    var doubanUrl: String = ""
}