package com.brainybeam.moviedemo.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by BrainyBeam on 05-Jan-19.
 * @author BrainyBeam
 */
data class MovieData(
    @Expose
    @SerializedName("id")
    val id: String,
    @Expose
    @SerializedName("title")
    val title: String,
    @Expose
    @SerializedName("genre_ids")
    val genreIds: ArrayList<GenreIds>,
    @Expose
    @SerializedName("age_category")
    val ageCategory: String,
    @Expose
    @SerializedName("rate")
    val rate: Float,
    @Expose()
    @SerializedName("release_date")
    val releaseDate: Long,
    @Expose()
    @SerializedName("poster_path")
    val posterPath: String,
    @Expose()
    @SerializedName("presale_flag")
    val preSaleFlag: Int,
    @Expose()
    @SerializedName("description")
    val description: String,
    var isLoadingView: Boolean = false
)