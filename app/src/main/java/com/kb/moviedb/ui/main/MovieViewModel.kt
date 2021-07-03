package com.kb.moviedb.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kb.moviedb.model.MovieListResponse
import com.kb.moviedb.network.MovieRepository
import kotlinx.coroutines.*

class MovieViewModel constructor(
    private val mainRepository: MovieRepository
) : ViewModel() {

    val errorMessage = MutableLiveData<String>()
    val movieList = MutableLiveData<MovieListResponse>()
    var job: Job? = null
    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception: ${throwable.localizedMessage}")
    }
    val loading = MutableLiveData<Boolean>()
    fun initialize() {
        getAllMovies()
    }

    private fun getAllMovies() {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = mainRepository.getMovies()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    Log.e("TAG", "Success")
                    movieList.postValue(response.body())
                    loading.value = false
                } else {
                    Log.e("TAG", "Failed")
                    onError("Error : ${response.message()} ")
                }
            }
        }

    }

    private fun onError(message: String) {
        errorMessage.value = message
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

}