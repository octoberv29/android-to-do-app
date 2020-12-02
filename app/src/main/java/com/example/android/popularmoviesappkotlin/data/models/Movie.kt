package com.example.android.popularmoviesappkotlin.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movie_table")
class Movie {

//    @PrimaryKey(autoGenerate = true)
//    private int databaseId;

    //    @PrimaryKey(autoGenerate = true)
    //    private int databaseId;
    @PrimaryKey(autoGenerate = false)
    private val id: Int? = null

    private val popularity: Double? = null

    @SerializedName("vote_count")
    private val voteCount: Double? = null

    @SerializedName("video")
    private val isVideo: Boolean? = null

    @SerializedName("poster_path")
    private val posterPath: String? = null

    @SerializedName("adult")
    private val isAdult: Boolean? = null

    @SerializedName("backdrop_path")
    private val backdropPath: String? = null

    @SerializedName("original_language")
    private val originalLanguage: String? = null

    @SerializedName("original_title")
    private val originalTitle: String? = null

//    @SerializedName("genre_ids")
//    private List<Integer> genreIds;

    //    @SerializedName("genre_ids")
    //    private List<Integer> genreIds;
    private val title: String? = null

    @SerializedName("vote_average")
    private val voteAverage: Double? = null

    private val overview: String? = null

    @SerializedName("release_date")
    private val releaseDate: String? = null

}