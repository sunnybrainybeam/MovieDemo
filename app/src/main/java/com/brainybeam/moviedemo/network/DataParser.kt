package com.brainybeam.moviedemo.network

import com.brainybeam.moviedemo.interfaces.APIResponseListener
import com.brainybeam.moviedemo.models.ApiResponse
import com.brainybeam.moviedemo.utility.Constant
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by BrainyBeam on 05-Jan-19.
 */
class DataParser private constructor() {

    companion object {
        private val dataParser: DataParser by lazy {
            DataParser()
        }

        fun getInstance(): DataParser {
            return dataParser
        }
    }

    fun <T> parseData(call: Call<ApiResponse<T>>, tag: String, apiResponseListener: APIResponseListener?) {
        call.enqueue(object : Callback<ApiResponse<T>> {
            override fun onResponse(call: Call<ApiResponse<T>>?, response: Response<ApiResponse<T>>?) {
                if (response != null && response.isSuccessful) {
                    if (response.body()?.success == true) {
                        apiResponseListener?.onApiResponse(response.body(), tag)
                    } else {
                        if (apiResponseListener != null) {
                            val commonResponse = ApiResponse<Any>()
                            commonResponse.success = false
                            commonResponse.message = response.body()?.message!!
                            apiResponseListener.onApiResponse(commonResponse, tag)
                        }
                    }
                } else {
                    if (apiResponseListener != null) {
                        val commonResponse = ApiResponse<Any>()
                        commonResponse.success = false
                        commonResponse.message = Constant.RESPONSE_FAIL_MESSAGE
                        apiResponseListener.onApiResponse(commonResponse, tag)
                    }
                }
            }

            override fun onFailure(call: Call<ApiResponse<T>>?, t: Throwable?) {
                if (apiResponseListener != null) {
                    val commonResponse = ApiResponse<Any>()
                    commonResponse.success = false
                    commonResponse.message = Constant.RESPONSE_FAIL_MESSAGE
                    apiResponseListener.onApiResponse(commonResponse, tag)
                }
            }
        })
    }


}