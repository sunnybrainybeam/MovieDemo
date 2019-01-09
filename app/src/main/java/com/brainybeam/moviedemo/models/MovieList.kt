package com.brainybeam.moviedemo.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by BrainyBeam on 07-Jan-19.
 * @author BrainyBeam
 */

data class MovieList(
    @Expose
    @SerializedName("upcoming")
    val upcomingMovieList: ArrayList<MovieData?>,

    @Expose
    @SerializedName("showing")
    val showingMovieList: ArrayList<MovieData?>
)
