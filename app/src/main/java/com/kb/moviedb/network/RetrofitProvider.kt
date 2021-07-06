package com.kb.moviedb.network

import com.kb.moviedb.utils.Constants.Companion.API_KEY
import com.kb.moviedb.utils.Constants.Companion.BASE_API_URL
import com.kb.moviedb.utils.Constants.Companion.REQUEST_TIMEOUT_DURATION
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class RetrofitProvider {
    private lateinit var retrofit: Retrofit

    fun getInstance(): Retrofit {
        if (!this::retrofit.isInitialized) {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_API_URL)
                .client(provideOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit
    }

    private fun provideOkHttpClient(): OkHttpClient {
        val okHttpBuilder = OkHttpClient.Builder()
            .addInterceptor(provideInterceptor())
            .connectTimeout(REQUEST_TIMEOUT_DURATION.toLong(), TimeUnit.SECONDS)
            .readTimeout(REQUEST_TIMEOUT_DURATION.toLong(), TimeUnit.SECONDS)
            .writeTimeout(REQUEST_TIMEOUT_DURATION.toLong(), TimeUnit.SECONDS)
        return okHttpBuilder.build()
    }

    private fun provideInterceptor() = Interceptor() { chain ->
        val original: Request = chain.request()
        val originalHttpUrl: HttpUrl = original.url
        val url = originalHttpUrl.newBuilder()
            .addQueryParameter("api_key", API_KEY)
            .build()
        val requestBuilder: Request.Builder = original.newBuilder()
            .url(url)
        val request: Request = requestBuilder
            .build()
        chain.proceed(request)
    }

}