package com.shiv.moviesapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movies")
data class Movie(
    @PrimaryKey
    @SerializedName("id")
    val id: Int,

    @SerializedName("title")
    val title: String = "",

    @SerializedName("overview")
    val overview: String = "",

    @SerializedName("poster_path")
    val posterPath: String? = null,

    @SerializedName("backdrop_path")
    val backdropPath: String? = null,

    @SerializedName("release_date")
    val releaseDate: String = "",

    @SerializedName("vote_average")
    val voteAverage: Double = 0.0,

    @SerializedName("popularity")
    val popularity: Double = 0.0,

    val isBookmarked: Boolean = false,

    val category: String = ""
)

