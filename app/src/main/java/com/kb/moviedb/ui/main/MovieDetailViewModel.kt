package com.kb.moviedb.ui.main

import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.text.style.BulletSpan
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kb.moviedb.model.MovieDetailResponse
import com.kb.moviedb.network.MovieRepository
import kotlinx.coroutines.*

class MovieDetailViewModel constructor(
    private val mainRepository: MovieRepository
) : ViewModel() {

    val errorMessage = MutableLiveData<String>()
    val movieDetail = MutableLiveData<MovieDetailResponse>()
    var job: Job? = null
    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception: ${throwable.localizedMessage}")
    }
    val loading = MutableLiveData<Boolean>()

    private fun getMoviesDetails() {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = mainRepository.getMovieDetails("337404")
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    Log.e("TAG", "Success")
                    movieDetail.postValue(response.body())
                    loading.value = false
                } else {
                    Log.e("TAG", "Failed")
                    onError("Error : ${response.message()} ")
                }
            }
        }

    }

    private fun getOtherDetails(detail: String): SpannableString {
        val text = SpannableString(detail)
        text.setSpan(BulletSpan(10, Color.YELLOW), 0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return text
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