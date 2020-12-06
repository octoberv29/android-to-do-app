package com.example.android.popularmoviesappkotlin.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.android.popularmoviesappkotlin.data.Repository
import com.example.android.popularmoviesappkotlin.data.models.Movie
import kotlinx.coroutines.launch


class MovieDetailsViewModel(application: Application, movieId: Int) : AndroidViewModel(application) {

    private val repository: Repository = Repository.getInstance(application)

    private val _movieDetails = MutableLiveData<Movie>()
    val movieDetails: LiveData<Movie>
        get() = _movieDetails

    init {
        getMovieDetails(movieId)
    }

    private fun getMovieDetails(movieId: Int) {
        viewModelScope.launch {
            _movieDetails.value = repository.getMovieDetails(movieId)
        }
    }

    class MovieDetailsViewModelFactory(private val application: Application, private val id: Int) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MovieDetailsViewModel(application, id) as T
        }
    }


}