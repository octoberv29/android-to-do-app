package com.example.android.popularmoviesappkotlin.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.android.popularmoviesappkotlin.data.models.Movie

@Database(entities = [Movie::class], version = 1)
abstract class MovieDatabase: RoomDatabase() {

    abstract fun movieDao(): MovieDao

    companion object {

        @Volatile
        private var INSTANCE: MovieDatabase? = null

        fun getInstance(context: Context): MovieDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room
                        .databaseBuilder(context.applicationContext, MovieDatabase::class.java, "movie_db")
                        .fallbackToDestructiveMigration()
                        .build()
                }
                INSTANCE = instance

                return instance
            }
        }
    }
}