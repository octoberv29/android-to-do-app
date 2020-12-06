package com.example.android.popularmoviesappkotlin.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.android.popularmoviesappkotlin.data.models.Movie

@Dao
interface MovieDao {

    @Insert
    suspend fun insert(movie: Movie)

    @Query("SELECT * FROM movie_table WHERE id ==:movieId")
    suspend fun getById(movieId: Int): Movie?

    @Query("SELECT EXISTS (SELECT 1 FROM movie_table WHERE id = :movieId)")
    suspend fun exists(movieId: Int): Boolean

    @Query("SELECT * FROM movie_table")
    fun getAll(): LiveData<List<Movie>>

    @Query("DELETE FROM movie_table WHERE id = :movieId")
    suspend fun deleteById(movieId: Int)

    @Query("DELETE FROM movie_table")
    suspend fun deleteAll()
}