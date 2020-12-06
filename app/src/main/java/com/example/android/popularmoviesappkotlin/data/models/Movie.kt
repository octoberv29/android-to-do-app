package com.example.android.popularmoviesappkotlin.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movie_table")
data class Movie (

    @PrimaryKey(autoGenerate = false)
    val id: Int? = null,

    val popularity: Double? = null,

    @SerializedName("vote_count")
    val voteCount: Double? = null,

    @SerializedName("video")
    val isVideo: Boolean? = null,

    @SerializedName("poster_path")
    val posterPath: String? = null,

    @SerializedName("adult")
    val isAdult: Boolean? = null,

    @SerializedName("backdrop_path")
    val backdropPath: String? = null,

    @SerializedName("original_language")
    val originalLanguage: String? = null,

    @SerializedName("original_title")
    val originalTitle: String? = null,

    val title: String? = null,

    @SerializedName("vote_average")
    val voteAverage: Double? = null,

    val overview: String? = null,

    @SerializedName("release_date")
    val releaseDate: String? = null
)