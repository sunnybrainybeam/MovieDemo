package com.brainybeam.moviedemo.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by BrainyBeam on 07-Jan-19.
 */
data class SearchData(
    var title: String = "",
    var timestamp: Long = 0
)