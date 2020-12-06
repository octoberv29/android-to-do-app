package com.example.android.popularmoviesappkotlin.data

import android.app.Application
import com.example.android.popularmoviesappkotlin.data.database.MovieDao
import com.example.android.popularmoviesappkotlin.data.database.MovieDatabase
import com.example.android.popularmoviesappkotlin.data.network.MovieApiService
import com.example.android.popularmoviesappkotlin.data.network.MovieRetrofit

class Repository2(application: Application) {

    private lateinit var repositoryInstance: Repository
    private lateinit var retrofitService: MovieApiService // for Retrofit
    private lateinit var mMovieDao: MovieDao // for Room

    init {
        retrofitService = MovieRetrofit.getInstance()?.create(MovieApiService::class.java)!!
        mMovieDao = MovieDatabase.getInstance(application).movieDao()
    }

}