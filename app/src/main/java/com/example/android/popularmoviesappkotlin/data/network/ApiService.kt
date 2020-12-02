package com.example.android.popularmoviesappkotlin.data.network

import com.example.android.popularmoviesappkotlin.data.models.MyModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("")
    suspend fun getMyModels(
        @Query("page") page: Int
    ): Call<MyModel>

    @GET("{id}")
    suspend fun getMyModelDetails(
        @Path("id") id: Long
    ): Call<MyModel?>
}