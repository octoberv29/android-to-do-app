package com.example.android.popularmoviesappkotlin.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.android.popularmoviesappkotlin.data.Repository
import com.example.android.popularmoviesappkotlin.data.models.Movie
import kotlinx.coroutines.launch


class FavouriteViewModel(
    application: Application,
    private val repository: Repository
) : AndroidViewModel(application) {

    //    private val repository: Repository = Repository.getInstance(application)
    private val allFavouriteMovies: LiveData<List<Movie>> = repository.getAllFavouriteMovies()

    fun getAllFavouriteMovies(): LiveData<List<Movie>> {
        return allFavouriteMovies
    }

    fun checkIfFavouriteMovieExistInDb(movieId: Int): Boolean? {
        var check: Boolean? = null
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

    class FavouriteViewModelFactory(
        private val application: Application,
        private val repository: Repository,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return FavouriteViewModel(application, repository) as T
        }
    }
}