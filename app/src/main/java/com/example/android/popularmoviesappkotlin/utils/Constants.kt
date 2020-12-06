package com.example.android.popularmoviesappkotlin.utils

class Constants {
    companion object {
        private val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/"
        private val POSTER_SIZE = "w342"
        private val BACKDROP_SIZE = "w780"

        val IMAGE_URL = IMAGE_BASE_URL + POSTER_SIZE
        val BACKDROP_URL = IMAGE_BASE_URL + BACKDROP_SIZE
    }
}