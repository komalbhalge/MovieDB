package com.kb.moviedb.network

class MovieRepository constructor(private val moviewService: MovieInterface) {
    suspend fun getPopularMovies() = moviewService.getPopularMovies()
}