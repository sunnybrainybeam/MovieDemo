package com.brainybeam.moviedemo.network

import com.brainybeam.moviedemo.models.ApiResponse
import com.brainybeam.moviedemo.models.MovieData
import retrofit2.Call
import retrofit2.http.GET

/**
 * Created by BrainyBeam on 05-Jan-19.
 */

const val HOME_MOVIE_LIST = "home"

interface ApiRepository {

    @GET(HOME_MOVIE_LIST)
    fun homeMovieList(): Call<ApiResponse<ArrayList<MovieData>>>
}