package com.example.android.popularmoviesappkotlin.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class FavouriteMoviesViewModel(application: Application) : AndroidViewModel(application) {

    class MovieViewModelFactory: ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            TODO("Not yet implemented")
        }

    }
}