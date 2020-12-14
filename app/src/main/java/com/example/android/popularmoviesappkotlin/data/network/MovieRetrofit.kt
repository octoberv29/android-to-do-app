package com.example.android.popularmoviesappkotlin.data.network

import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MovieRetrofit {
    companion object {

        private const val BASE_URL = "https://api.themoviedb.org/3/"
        private var INSTANCE: Retrofit? = null

        fun getInstance(): Retrofit? {
            if (INSTANCE == null) {

                val authInterceptor = Interceptor { chain ->

                    val url: HttpUrl = chain.request().url.newBuilder()
                        .addQueryParameter("api_key", "9321c4fc5f95b92bce700096da663cde")
                        .build()

                    val request: Request = chain.request().newBuilder()
//                        .addHeader("Authorization", "9321c4fc5f95b92bce700096da663cde")
                        .url(url)
                        .build()

                    chain.proceed(request)
                }

                val client: OkHttpClient = OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
                    .addInterceptor(authInterceptor)
                    .build()

                val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build()

                INSTANCE = retrofit
            }
            return INSTANCE
        }

    }
}