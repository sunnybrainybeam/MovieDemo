package com.brainybeam.moviedemo.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by BrainyBeam on 05-Jan-19.
 */
data class MovieData(
    val id: String,
    val title: String,
    @Expose
    @SerializedName("genre_ids")
    val genreIds: ArrayList<GenreIds>,
    @Expose
    @SerializedName("age_category")
    val appCategory: String,
    val rate: String,
    @Expose()
    @SerializedName("release_date")
    val releaseDate: String,
    @Expose()
    @SerializedName("poster_path")
    val posterPath: String,
    @Expose()
    @SerializedName("presale_flag")
    val preSaleFlag: Int
)