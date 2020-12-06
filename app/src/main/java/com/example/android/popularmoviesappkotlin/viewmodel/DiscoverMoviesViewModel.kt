package com.example.android.popularmoviesappkotlin.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.popularmoviesappkotlin.data.Repository
import com.example.android.popularmoviesappkotlin.data.models.MovieResponse


class DiscoverMoviesViewModel(application: Application, sortBy: String) : AndroidViewModel(application) {
    val movieResponseObservable: LiveData<MovieResponse>

    init {
        movieResponseObservable = Repository(application).getMovies(sortBy, 1)
    }

    class DiscoverMoviesViewModelFactory(
        private val application: Application,
        private val sortBy: String
    ) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return DiscoverMoviesViewModel(application, sortBy) as T
        }
    }


}