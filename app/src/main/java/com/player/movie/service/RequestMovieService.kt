package com.player.movie.service

import com.player.constant.RelationType
import com.player.http.ResultEntity
import com.player.movie.api.Api
import com.player.movie.entity.MovieEntity
import com.player.movie.entity.UserEntity
import retrofit2.Call
import retrofit2.http.*

interface RequestMovieService {
    @get:GET(Api.GETUSERDATA)
    val userData: Call<ResultEntity>

    @GET(Api.GETCATEGORYLIST)
    fun getCategoryList(
        @Query("category") category: String?,
        @Query("classify") classify: String?
    ): Call<ResultEntity>

    @GET(Api.GETALLCATEGORYLISTBYPAGENAME)
    fun getAllCategoryListByPageName(@Query("pageName") pageName: String?): Call<ResultEntity>

    @GET(Api.GETKEYWORD)
    fun getKeyWord(@Query("classify") classify: String): Call<ResultEntity>

    @GET(Api.GETUSERMSG)
    fun getUserMsg(): Call<ResultEntity>

    @GET(Api.GETPLAYRECORD)
    fun getPlayRecord(@Query("pageNum") pageNum: Int,@Query("pageSize") pageSize: Int): Call<ResultEntity>

    @GET(Api.GETFAVORITE)
    fun getFavoriteList(@Query("pageNum") pageNum: Int,@Query("pageSize") pageSize: Int): Call<ResultEntity>

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

    @POST(Api.LOGIN)
    fun login(@Body userEntity: UserEntity): Call<ResultEntity>

    @PUT(Api.UPDATEUSER)
    fun updateUser(@Body userEntity: UserEntity?): Call<ResultEntity?>?

    @GET(Api.GETSTAR)
    fun getStar(@Path("movieId")movieId:Long): Call<ResultEntity>

    @GET(Api.GETCOMMENTCOUNT)
    fun getCommentCount(
        @Query("relationId") relationId: Long,
        @Query("type") type: String,
    ): Call<ResultEntity>

    @POST(Api.SAVEPLAYRECORD)
    fun savePlayRecord(@Body movieEntity: MovieEntity): Call<ResultEntity>

    @POST(Api.SAVEVIEWRECORD)
    fun saveViewRecord(@Body movieEntity: MovieEntity): Call<ResultEntity>

    @GET(Api.GETMOVIEURL)
    fun getMovieUrl(@Query("movieId")movieId:Long): Call<ResultEntity>

    @GET(Api.ISFAVORITE)
    fun isFavorite(@Query("movieId")movieId:Long):Call<ResultEntity>

    // 删除收藏
    @DELETE(Api.DELETEFAVORITE)
    fun deleteFavorite(@Path("movieId")movieId:Long):Call<ResultEntity>

    // 添加收藏
    @DELETE(Api.SAVEFAVORITE)
    fun saveFavorite(@Path("movieId")movieId:Long):Call<ResultEntity>

}