package com.brainybeam.moviedemo.network

import com.brainybeam.moviedemo.models.ApiResponse
import com.brainybeam.moviedemo.models.MovieData
import com.brainybeam.moviedemo.models.MovieList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by BrainyBeam on 05-Jan-19.
 */

const val HOME_MOVIE_LIST = "home"
const val SEARCH_MOVIE = "search"

interface ApiRepository {

    /**
     * Use to get data of home screen movies.
     */
    @GET(HOME_MOVIE_LIST)
    fun homeMovieList(): Call<ApiResponse<ArrayList<MovieData>>>


    /**
     * Use to get movie list as per searching keyword and page no
     */
    @GET(SEARCH_MOVIE)
    fun getSearchMovieList(@Query("keyword") searchKeyword: String, @Query("offset") pageNo: Int): Call<ApiResponse<MovieList>>
}