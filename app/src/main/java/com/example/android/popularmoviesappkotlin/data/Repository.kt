package com.example.android.popularmoviesappkotlin.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android.popularmoviesappkotlin.data.database.MovieDao
import com.example.android.popularmoviesappkotlin.data.database.MovieDatabase
import com.example.android.popularmoviesappkotlin.data.models.Movie
import com.example.android.popularmoviesappkotlin.data.models.MovieResponse
import com.example.android.popularmoviesappkotlin.data.network.MovieApiService
import com.example.android.popularmoviesappkotlin.data.network.MovieRetrofit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository private constructor(application: Application) {
    // Retrofit
    private val retrofitService: MovieApiService = MovieRetrofit.getInstance()?.create(MovieApiService::class.java)!!
    // Room
    private val mMovieDao: MovieDao = MovieDatabase.getInstance(application).movieDao()
    private val mAllFavouriteMovies: LiveData<List<Movie>>

    companion object {
        @Volatile
        private var INSTANCE: Repository? = null

        fun getInstance(application: Application): Repository {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Repository(application)
                }
                INSTANCE = instance
                return instance
            }
        }
    }

    init {
        mAllFavouriteMovies = mMovieDao.getAll()
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
        mMovieDao.exists(movieId)
    }

    suspend fun getFavouriteMovieById(movieId: Int): Movie? = withContext(IO) {
        mMovieDao.getById(movieId)
    }

    suspend fun insertFavouriteMovie(movieToInsert: Movie) = withContext(IO) {
        mMovieDao.insert(movieToInsert)
    }

    suspend fun deleteFavouriteMovieById(idToDelete: Int) = withContext(IO) {
        mMovieDao.deleteById(idToDelete)
    }

    suspend fun deleteAllFavouriteMovies() = withContext(IO) {
        mMovieDao.deleteAll()
    }


    // for future cache functionality
    suspend fun refreshMoviesList(sortBy: String?, page: Int) = withContext(IO) {
        var data: MovieResponse = retrofitService.getMovies(sortBy, page)
        // replace by insertAll and a different table or somethign else
//            mMovieDao.insert(data.movies)
    }
}