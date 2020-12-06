package com.example.android.popularmoviesappkotlin.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.android.popularmoviesappkotlin.data.Repository
import com.example.android.popularmoviesappkotlin.data.models.MovieResponse
import kotlinx.coroutines.launch


class DiscoverMoviesViewModel(application: Application, sortBy: String, page: Int) : AndroidViewModel(application) {

    private val repository: Repository = Repository.getInstance(application)

    private val _movieResponse = MutableLiveData<MovieResponse>()
    val movieResponse: LiveData<MovieResponse>
        get() = _movieResponse

    init {
        getMovies(sortBy, page)
    }

    private fun getMovies(sortBy: String, page: Int) {
        viewModelScope.launch {
            _movieResponse.value = repository.getMovies(sortBy, page)
        }
    }

    class DiscoverMoviesViewModelFactory(private val application: Application, private val sortBy: String, private val page: Int) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return DiscoverMoviesViewModel(application, sortBy, page) as T
        }
    }


}