package com.kb.moviedb.network

import android.util.Log.DEBUG
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

object RetrofitClient {
    val instance: MovieApi = Retrofit.Builder().run {
        RetrofitProvider().getInstance()
    }.create(MovieApi::class.java)
}