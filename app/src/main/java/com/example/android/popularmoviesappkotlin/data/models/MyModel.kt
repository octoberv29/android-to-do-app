package com.example.android.popularmoviesappkotlin.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "my_table")
data class MyModel(

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,

    @SerializedName("title")
    @ColumnInfo(name = "")
    val title : String = ""
)