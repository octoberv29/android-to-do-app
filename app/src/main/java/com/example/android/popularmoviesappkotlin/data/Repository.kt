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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository private constructor(application: Application) {
    private val retrofitService: MovieApiService

    // Room
    private val mMovieDao: MovieDao
    private val mAllFavouriteMovies: LiveData<List<Movie>>

    companion object {
        @Volatile
        private var INSTANCE: Repository? = null

        fun getInstance(application: Application): Repository? {
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
        // for Retrofit
        retrofitService = MovieRetrofit.getInstance()?.create(MovieApiService::class.java)!!

        // for Room
        mMovieDao = MovieDatabase.getInstance(application).movieDao()
        mAllFavouriteMovies = mMovieDao.getAll()
    }

    // for Retrofit
    fun getMovies(sortBy: String?, page: Int): LiveData<MovieResponse> {
        val data = MutableLiveData<MovieResponse>()

        retrofitService.getMovies(sortBy, page).enqueue(object: Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                data.value = response.body()
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                //
            }

        })
        return data
    }

    fun getMovieDetails(id: Long): LiveData<Movie> {
        val data: MutableLiveData<Movie> = MutableLiveData<Movie>()

        retrofitService.getMovieDetails(id).enqueue(object: Callback<Movie> {
            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                data.value = response.body()
            }

            override fun onFailure(call: Call<Movie>, t: Throwable) {
                //
            }

        })
        return data
    }


    // for Room
    fun getAllFavouriteMovies(): LiveData<List<Movie>> {
        return mAllFavouriteMovies
    }

    fun insertFavouriteMovie(movieToInsert: Movie) {
        CoroutineScope(IO).launch {
            mMovieDao.insert(movieToInsert)
        }
    }

    fun deleteFavouriteMovieById(idToDelete: Int) {
        CoroutineScope(IO).launch {
            mMovieDao.deleteById(idToDelete)
        }
    }

    fun deleteAllFavouriteMovies() {
        CoroutineScope(IO).launch {
            mMovieDao.deleteAll()
        }
    }
}