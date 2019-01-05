package com.brainybeam.moviedemo.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.brainybeam.moviedemo.interfaces.APIResponseListener
import com.brainybeam.moviedemo.models.ApiResponse
import com.brainybeam.moviedemo.models.MovieData
import com.brainybeam.moviedemo.network.ApiRepository
import com.brainybeam.moviedemo.network.DataParser
import com.brainybeam.moviedemo.network.HOME_MOVIE_LIST
import com.brainybeam.moviedemo.network.RetrofitClient

/**
 * Created by BrainyBeam on 05-Jan-19.
 */
class MovieViewModel : ViewModel() {

    private var homeMovieMutableLiveData: MutableLiveData<ApiResponse<*>>? = null


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

}