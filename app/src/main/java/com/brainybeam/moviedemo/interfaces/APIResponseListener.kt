package com.brainybeam.moviedemo.interfaces

import com.brainybeam.moviedemo.models.ApiResponse

/**
 * Created by BrainyBeam on 05-Jan-19.
 */
interface APIResponseListener {
    fun onApiResponse(apiResponse: ApiResponse<*>?, key: String)
}