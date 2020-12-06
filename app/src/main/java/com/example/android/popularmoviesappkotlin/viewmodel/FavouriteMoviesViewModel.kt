package com.example.android.popularmoviesappkotlin.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.android.popularmoviesappkotlin.data.Repository
import com.example.android.popularmoviesappkotlin.data.models.Movie


class FavouriteMoviesViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: Repository
    private val allFavouriteMovies: LiveData<List<Movie>>

    init {
        repository = Repository(application)
        allFavouriteMovies = repository.getAllFavouriteMovies()
    }

    fun getAllFavouriteMovies(): LiveData<List<Movie>> {
        return allFavouriteMovies
    }

    fun insertFavouriteMovie(movie: Movie?) {
        repository.insertFavouriteMovie(movie)
    }

    fun deleteFavouriteMovieById(id: Int) {
        repository.deleteFavouriteMovieById(id)
    }

    fun deleteAllFavouriteMovies() {
        repository.deleteAllFavouriteMovies()
    }


}