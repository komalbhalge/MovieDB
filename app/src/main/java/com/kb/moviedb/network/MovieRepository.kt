package com.kb.moviedb.network

class MovieRepository {
    suspend fun getMovies() = RetrofitClient.instance.getPopularMovies()
    suspend fun getMovieDetails(id: String) = RetrofitClient.instance.getMovieDetails(id)
    companion object {
        private var INSTANCE: MovieRepository? = null
        fun getInstance() = INSTANCE
            ?: MovieRepository().also {
                INSTANCE = it
            }
    }
}