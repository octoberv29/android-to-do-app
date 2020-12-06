package com.example.android.popularmoviesappkotlin.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.popularmoviesappkotlin.data.Repository
import com.example.android.popularmoviesappkotlin.data.models.Movie


class MovieDetailsViewModel(application: Application, movieId: Long) : AndroidViewModel(application) {

    private val repository: Repository
    private val movieDetailsObservable: LiveData<Movie>

    init {
        repository = Repository.getInstance(application)!!
        movieDetailsObservable = repository.getMovieDetails(movieId)
    }

    fun getMovieDetailsObservable(): LiveData<Movie> {
        return movieDetailsObservable
    }

    class MovieDetailsViewModelFactory(private val application: Application, private val id: Long) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MovieDetailsViewModel(application, id) as T
        }
    }


}