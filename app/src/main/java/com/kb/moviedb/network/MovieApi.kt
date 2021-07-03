package com.kb.moviedb.network

import com.kb.moviedb.model.MovieListResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {
    @GET("movie/popular")
    suspend fun getPopularMovies(): Response<MovieListResponse>
}