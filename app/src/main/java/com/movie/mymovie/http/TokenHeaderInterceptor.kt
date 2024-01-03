package com.movie.mymovie.http


import okhttp3.Interceptor.Chain
import okhttp3.OkHttpClient.Builder
import okhttp3.Interceptor
import kotlin.Throws
import java.io.IOException
import com.movie.mymovie.BaseApplication
import okhttp3.Response
import java.util.concurrent.TimeUnit

class TokenHeaderInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Chain): Response {
        return chain.proceed(chain.request())
    }

    val client: Builder
        get() {
            val builder = Builder()
            builder.connectTimeout(15, TimeUnit.SECONDS)
            val token: String = BaseApplication.instance.token
            builder.addInterceptor(Interceptor { chain: Interceptor.Chain ->
                val build = chain.request().newBuilder()
                    .addHeader("Authorization", token)
                    .addHeader("Content-type", "application/json;charset=UTF-8")
                    .build()
                chain.proceed(build)
            })
            return builder
        }
}