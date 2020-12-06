package com.example.android.popularmoviesappkotlin.data.network


import com.example.android.popularmoviesappkotlin.data.models.Movie
import com.example.android.popularmoviesappkotlin.data.models.MovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface MovieApiService {

    // TODO: change to suspend

    @GET("discover/movie")
    fun getMovies(
        @Query("sort_by") sortBy: String?,
        @Query("page") page: Int
    ): Call<MovieResponse>

    @GET("movie/{movie_id}")
    fun getMovieDetails(
        @Path("movie_id") id: Long
    ): Call<Movie>
}