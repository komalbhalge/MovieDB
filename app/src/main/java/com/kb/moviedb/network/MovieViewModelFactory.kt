package com.kb.moviedb.network

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kb.moviedb.ui.main.MovieDetailViewModel
import com.kb.moviedb.ui.main.MovieViewModel

class MovieViewModelFactory constructor(private val repository: MovieRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MovieViewModel::class.java)) {
            MovieViewModel(this.repository) as T
        }else if (modelClass.isAssignableFrom(MovieDetailViewModel::class.java)) {
            MovieDetailViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}
