package com.movie.mymovie.http

import com.movie.mymovie.movie.api.Api
import com.movie.mymovie.movie.entity.UserEntity
import retrofit2.Call
import retrofit2.http.*

interface RequestService {
    @get:GET(Api.GETUSERDATA)
    val userData: Call<ResultEntity>

    @GET(Api.GETCATEGORYLIST)
    fun getCategoryList(
        @Query("category") category: String?,
        @Query("classify") classify: String?
    ): Call<ResultEntity>

    @GET(Api.GETALLCATEGORYLISTBYPAGENAME)
    fun getAllCategoryListByPageName(@Query("pageName") pageName: String?): Call<ResultEntity?>?

    @GET(Api.GETKEYWORD)
    fun getKeyWord(@Query("classify") classify: String): Call<ResultEntity>

    @get:GET(Api.GETUSERMSG)
    val userMsg: Call<ResultEntity?>?

    @get:GET(Api.GETPLAYRECORD)
    val playRecord: Call<ResultEntity?>?

    @GET(Api.GETSTAR)
    fun getStarList(@Path("movieId") movieId: String?): Call<ResultEntity?>?

    @GET(Api.GETYOURLIKES)
    fun getYourLikes(
        @Query("labels") labels: String?,
        @Query("classify") classify: String?
    ): Call<ResultEntity?>?

    @GET(Api.GETRECOMMEND)
    fun getRecommend(@Query("classify") classify: String?): Call<ResultEntity?>?

    @GET(Api.GETTOPMOVIELIST)
    fun getTopMovieList(
        @Query("classify") classify: String?,
        @Query("category") category: String?
    ): Call<ResultEntity?>?

    @GET(Api.GETMOVIEURL)
    fun getMovieUrl(@Query("movieId") movieId: Long?): Call<ResultEntity?>?

    // 搜索
    @GET(Api.GETSEARCHRESULT)
    fun search(
        @Query("classify") classify: String?,
        @Query("category") category: String?,
        @Query("label") label: String?,
        @Query("star") star: String?,
        @Query("director") director: String?,
        @Query("keyword") keyword: String?,
        @Query("pageSize") pageSize: Int,
        @Query("pageNum") pageNum: Int
    ): Call<ResultEntity?>?

    @PUT(Api.UPDATEUSER)
    fun updateUser(@Body userEntity: UserEntity?): Call<ResultEntity?>?
}