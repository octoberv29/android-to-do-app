package com.example.android.popularmoviesappkotlin.data

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.android.popularmoviesappkotlin.data.database.MovieDao
import com.example.android.popularmoviesappkotlin.data.models.Movie
import com.example.android.popularmoviesappkotlin.data.models.MovieResponse
import com.example.android.popularmoviesappkotlin.data.network.MovieApiService
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class Repository private constructor(
    private val retrofitService: MovieApiService,
    private val movieDao: MovieDao
) {
    // Retrofit
//    private val retrofitService = MovieRetrofit.getInstance()?.create(MovieApiService::class.java)!!
    // Room
//    private val movieDao: MovieDao = MovieDatabase.getInstance(application).movieDao()
    private val mAllFavouriteMovies: LiveData<List<Movie>>

    companion object {
        @Volatile
        private var INSTANCE: Repository? = null

        fun getInstance(retrofitService: MovieApiService, movieDao: MovieDao): Repository {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Repository(retrofitService, movieDao)
                }
                INSTANCE = instance
                return instance
            }
        }
    }

    init {
        mAllFavouriteMovies = movieDao.getAll()
    }

    suspend fun getMovies(sortBy: String?, page: Int): MovieResponse = withContext(IO) {
        retrofitService.getMovies(sortBy, page)
    }

    suspend fun getMovieDetails(id: Int): Movie = withContext(IO) {
        retrofitService.getMovieDetails(id)
    }

    // for Room
    fun getAllFavouriteMovies(): LiveData<List<Movie>> {
        return mAllFavouriteMovies
    }

    suspend fun checkIfFavouriteMovieExistInDb(movieId: Int): Boolean = withContext(IO) {
        movieDao.exists(movieId)
    }

    suspend fun getFavouriteMovieById(movieId: Int): Movie? = withContext(IO) {
        movieDao.getById(movieId)
    }

    suspend fun insertFavouriteMovie(movieToInsert: Movie) = withContext(IO) {
        movieDao.insert(movieToInsert)
    }

    suspend fun deleteFavouriteMovieById(idToDelete: Int) = withContext(IO) {
        movieDao.deleteById(idToDelete)
    }

    suspend fun deleteAllFavouriteMovies() = withContext(IO) {
        movieDao.deleteAll()
    }


    // for future cache functionality
    suspend fun refreshMoviesList(sortBy: String?, page: Int) = withContext(IO) {
        var data: MovieResponse = retrofitService.getMovies(sortBy, page)
        // replace by insertAll and a different table or something else
//            mMovieDao.insert(data.movies)
    }
}