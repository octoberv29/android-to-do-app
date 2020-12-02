package com.example.android.popularmoviesappkotlin.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyRetrofit {
    companion object {

        private const val BASE_URL = "https://api.themoviedb.org/3/"
        private var INSTANCE: Retrofit? = null

        fun getInstance(): Retrofit? {
            if (INSTANCE == null) {

//                val client: OkHttpClient = OkHttpClient.Builder()
//                    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
//                    .addInterceptor(AuthInterceptor())
//                    .build()

                val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
//                    .client(client)
                    .build()
                INSTANCE = retrofit
            }
            return INSTANCE
        }

//        class AuthInterceptor : Interceptor {
//            override fun intercept(chain: Interceptor.Chain): Response {
//                var request: Request = chain.request()
//                val url: HttpUrl = request.url.newBuilder()
//                    .addQueryParameter("api_key", "9321c4fc5f95b92bce700096da663cde")
//                    .build()
//                request = request.newBuilder().url(url).build()
//                return chain.proceed(request)
//            }
//        }
    }
}