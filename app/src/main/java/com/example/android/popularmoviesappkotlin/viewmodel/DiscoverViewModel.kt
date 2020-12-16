package com.example.android.popularmoviesappkotlin.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.android.popularmoviesappkotlin.data.Repository
import com.example.android.popularmoviesappkotlin.data.models.MovieResponse
import kotlinx.coroutines.launch
import java.io.IOException


class DiscoverViewModel(
    application: Application,
    private val repository: Repository,
    sortBy: String,
    page: Int
) : AndroidViewModel(application) {

//     = Repository.getInstance(application)

    private val _movieResponse = MutableLiveData<MovieResponse>()
    val movieResponse: LiveData<MovieResponse>
        get() = _movieResponse

    private var _eventNetworkError = MutableLiveData<Boolean>(false)
    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)
    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    init {
        getMovies(sortBy, page)
    }

    private fun getMovies(sortBy: String, page: Int) {
        viewModelScope.launch {
            try {
                _movieResponse.value = repository.getMovies(sortBy, page)
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

    class DiscoverViewModelFactory(
        private val application: Application,
        private val repository: Repository,
        private val sortBy: String,
        private val page: Int
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return DiscoverViewModel(application, repository, sortBy, page) as T
        }
    }
}