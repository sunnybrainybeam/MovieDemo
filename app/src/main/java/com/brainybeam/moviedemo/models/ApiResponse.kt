package com.brainybeam.moviedemo.models

/**
 * Created by BrainyBeam on 05-Jan-19.
 */
data class ApiResponse<T>(
    var message: String = "",
    var results: T? = null,
    var success: Boolean = false,
    var key: String? = ""
)