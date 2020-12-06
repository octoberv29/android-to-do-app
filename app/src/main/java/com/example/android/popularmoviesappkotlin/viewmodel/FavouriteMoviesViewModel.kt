package com.example.android.popularmoviesappkotlin.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.android.popularmoviesappkotlin.data.Repository
import com.example.android.popularmoviesappkotlin.data.models.Movie
import kotlinx.coroutines.launch


class FavouriteMoviesViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: Repository = Repository.getInstance(application)
    private val allFavouriteMovies: LiveData<List<Movie>> = repository.getAllFavouriteMovies()

    fun getAllFavouriteMovies(): LiveData<List<Movie>> {
        return allFavouriteMovies
    }

    fun checkIfFavouriteMovieExistInDb(movieId: Int): Boolean {
        var check = false
        viewModelScope.launch {
            check = repository.checkIfFavouriteMovieExistInDb(movieId)
        }
        return check
    }

    fun getFavouriteMovieById(movieId: Int): Movie? {
        var movie: Movie? = null
        viewModelScope.launch {
            movie = repository.getFavouriteMovieById(movieId)
        }
        return movie
    }

    fun insertFavouriteMovie(movie: Movie) {
        viewModelScope.launch {
            repository.insertFavouriteMovie(movie)
        }
    }

    fun deleteFavouriteMovieById(movieId: Int) {
        viewModelScope.launch {
            repository.deleteFavouriteMovieById(movieId)
        }
    }

    fun deleteAllFavouriteMovies() {
        viewModelScope.launch {
            repository.deleteAllFavouriteMovies()
        }
    }
}