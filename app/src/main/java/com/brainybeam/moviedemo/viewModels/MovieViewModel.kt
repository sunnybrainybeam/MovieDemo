package com.brainybeam.moviedemo.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.brainybeam.moviedemo.interfaces.APIResponseListener
import com.brainybeam.moviedemo.models.ApiResponse
import com.brainybeam.moviedemo.models.MovieData
import com.brainybeam.moviedemo.network.*

/**
 * Created by BrainyBeam on 05-Jan-19.
 */
class MovieViewModel : ViewModel() {

    private var homeMovieMutableLiveData: MutableLiveData<ApiResponse<*>>? = null
    private var movieListMutableLiveData: MutableLiveData<ApiResponse<*>>? = null


    /**
     * Use for get data of home screen movies.
     */
    fun getHomeMovieList(): LiveData<ApiResponse<*>> {
        homeMovieMutableLiveData = MutableLiveData()
        val call = RetrofitClient.getApiRepository().homeMovieList()
        DataParser.getInstance().parseData(call, HOME_MOVIE_LIST, object : APIResponseListener {
            override fun onApiResponse(apiResponse: ApiResponse<*>?, key: String) {
                if (apiResponse != null && key == HOME_MOVIE_LIST) {
                    homeMovieMutableLiveData!!.postValue(apiResponse)
                }
            }
        })
        return homeMovieMutableLiveData as MutableLiveData<ApiResponse<*>>
    }


    fun getSearchMovieList(searchKeyword: String, pageNo: Int): LiveData<ApiResponse<*>> {
        movieListMutableLiveData = MutableLiveData()
        val call = RetrofitClient.getApiRepository().getSearchMovieList(searchKeyword, pageNo)
        DataParser.getInstance().parseData(call, SEARCH_MOVIE, object : APIResponseListener {
            override fun onApiResponse(apiResponse: ApiResponse<*>?, key: String) {
                if (apiResponse != null && key == SEARCH_MOVIE) {
                    movieListMutableLiveData!!.postValue(apiResponse)
                }
            }
        })
        return movieListMutableLiveData as MutableLiveData<ApiResponse<*>>

    }
}