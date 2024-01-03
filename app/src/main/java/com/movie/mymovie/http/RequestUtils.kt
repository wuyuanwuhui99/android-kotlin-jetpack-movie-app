package com.movie.mymovie.http


import retrofit2.Retrofit
import com.movie.mymovie.api.Api
import retrofit2.converter.gson.GsonConverterFactory

object RequestUtils {
    val instance: RequestService
        get() = Retrofit.Builder()
            .baseUrl(Api.HOST)
            .client(TokenHeaderInterceptor().client.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(RequestService::class.java)
}