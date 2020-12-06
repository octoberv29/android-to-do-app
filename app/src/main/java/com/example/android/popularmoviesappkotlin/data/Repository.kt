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

    // for Retrofit
    suspend fun getMovies(sortBy: String?, page: Int): MovieResponse? {
//        val data = MutableLiveData<MovieResponse>()
//        retrofitService.getMovies(sortBy, page).enqueue(object: Callback<MovieResponse> {
//            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
//                data.value = response.body()
//            }
//
//            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
//                //
//            }
//
//        })
        var data: MovieResponse? = null
        try {
            data = retrofitService.getMovies(sortBy, page)
        } catch (e: Exception) {
            // TODO: ???
        }
        return data
    }

    suspend fun getMovieDetails(id: Int): Movie? {
        var data: Movie? = null
//        retrofitService.getMovieDetails(id).enqueue(object: Callback<Movie> {
//            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
//                data.value = response.body()
//            }
//
//            override fun onFailure(call: Call<Movie>, t: Throwable) {
//                //
//            }
//
//        })
            try {
                data = retrofitService.getMovieDetails(id)
            } catch (e: Exception) {
                // TODO: ???
            }
        return data
    }


    // for Room
    fun getAllFavouriteMovies(): LiveData<List<Movie>> {
        return mAllFavouriteMovies
    }

    suspend fun checkIfFavouriteMovieExistInDb(movieId: Int): Boolean {
        return mMovieDao.exists(movieId)
    }

    suspend fun getFavouriteMovieById(movieId: Int): Movie? {
        return mMovieDao.getById(movieId)
    }

    suspend fun insertFavouriteMovie(movieToInsert: Movie) {
        mMovieDao.insert(movieToInsert)
    }

    suspend fun deleteFavouriteMovieById(idToDelete: Int) {
        mMovieDao.deleteById(idToDelete)
    }

    suspend fun deleteAllFavouriteMovies() {
        mMovieDao.deleteAll()
    }
}