package com.player.http


import com.player.constant.Constant
import com.player.movie.service.RequestMovieService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RequestUtils {
    val movieInstance: RequestMovieService
        get() = Retrofit.Builder()
            .baseUrl(Constant.HOST)
            .client(TokenHeaderInterceptor().client.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(RequestMovieService::class.java)
}