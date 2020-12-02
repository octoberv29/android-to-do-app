package com.example.android.popularmoviesappkotlin.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.android.popularmoviesappkotlin.data.models.MyModel

@Dao
interface MyDao {

    @Insert
    suspend fun insert(myModel: MyModel)

    @Update
    suspend fun update(myModel: MyModel)

    @Query("SELECT * from my_table WHERE id = :key")
    suspend fun get(key: Long)

    @Query("DELETE FROM my_table")
    suspend fun clear()

    @Query("SELECT * FROM my_table ORDER BY id DESC")
    fun getAllMyModels(): LiveData<List<MyModel>>
}