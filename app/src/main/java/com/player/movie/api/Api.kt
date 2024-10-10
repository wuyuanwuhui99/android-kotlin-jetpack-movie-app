package com.player.movie.api

object Api {
    //查询所有大分类
    const val GETUSERDATA = "/service/user/getUserData"
    const val GETCATEGORYLIST = "/service/movie/getCategoryList" // 获取分类影片
    const val GETTOPMOVIELIST = "/service/movie/getTopMovieList" // 根据分类前20条影片数据
    const val GETKEYWORD = "/service/movie/getKeyWord" //按照classify查询搜索栏的关键词
    const val GETALLCATEGORYBYCLASSIFY = "/service/movie/getAllCategoryByClassify" //按classify大类查询所有catory小类
    const val GETALLCATEGORYLISTBYPAGENAME = "/service/movie/getAllCategoryListByPageName" //按页面获取要展示的category小类
    const val GETUSERMSG = "/service/movie-getway/getUserMsg" //获取用户四个指标信息，使用天数，关注，观看记录，浏览记录
    const val GETSEARCHRESULT = "/service/movie/search" //搜索
    const val LOGIN = "/service/user/login" //登录
    const val GETSTAR = "/service/movie/getStar/{movieId}" //获取演员
    const val GETMOVIEURL = "/service/movie/getMovieUrl" //获取电影播放地址
    const val GETVIEWRECORD = "/service/movie-getway/getViewRecord" //获取浏览记录
    const val SAVEVIEWRECORD = "/service/movie-getway/saveViewRecord" //浏览历史
    const val GETPLAYRECORD = "/service/movie-getway/getPlayRecord" //获取观看记录
    const val SAVEPLAYRECORD = "/service/movie-getway/savePlayRecord" //播放记录
    const val GETFAVORITE = "/service/movie-getway/getFavorite" //获取收藏电影
    const val SAVEFAVORITE = "/service/movie-getway/saveFavorite" //添加收藏
    const val DELETEFAVORITE = "/service/movie-getway/deleteFavorite" //删除收藏
    const val GETYOURLIKES = "/service/movie/getYourLikes" //猜你想看
    const val GETRECOMMEND = "/service/movie/getRecommend" //获取推荐
    const val ISFAVORITE = "/service/movie-getway/isFavorite" //查询是否已经收藏
    const val UPDATEUSER = "/service/movie-getway/updateUser" //更新用户信息
    const val UPDATEPASSWORD = "/service/movie-getway/updatePassword" //更新密码
    const val GETCOMMENTCOUNT = "/service/social/getCommentCount" //获取评论总数
    const val GETTOPCOMMENTLIST = "/service/social/getTopCommentList" //获取一级评论
    const val GETREPLYCOMMENTLIST = "/service/social/getReplyCommentList" //获取一级评论
    const val INSERTCOMMENTSERVICE = "/service/social-getway/insertComment" //新增评论
}