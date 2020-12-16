package com.example.android.popularmoviesappkotlin.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.android.popularmoviesappkotlin.data.Repository
import com.example.android.popularmoviesappkotlin.data.models.Movie
import kotlinx.coroutines.launch
import java.io.IOException


class DetailsViewModel(
    application: Application,
    private val repository: Repository,
    movieId: Int
) : AndroidViewModel(application) {

//    private val repository: Repository = Repository.getInstance(application)

    private val _movieDetails = MutableLiveData<Movie>()
    val movieDetails: LiveData<Movie>
        get() = _movieDetails

    private var _eventNetworkError = MutableLiveData<Boolean>(false)
    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)
    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    init {
        getMovieDetails(movieId)
    }

    private fun getMovieDetails(movieId: Int) {
        viewModelScope.launch {
            try {
                _movieDetails.value = repository.getMovieDetails(movieId)
                _eventNetworkError.value = false
                _isNetworkErrorShown.value = false
            } catch (networkError: IOException) {
                // Show a Toast error message and hide the progress bar.
                _eventNetworkError.value = true
            }
        }
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    class DetailsViewModelFactory(
        private val application: Application,
        private val repository: Repository,
        private val id: Int
    ) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return DetailsViewModel(application, repository, id) as T
        }
    }


}