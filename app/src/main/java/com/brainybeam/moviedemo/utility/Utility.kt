package com.brainybeam.moviedemo.utility

import android.content.Context
import android.net.ConnectivityManager
import android.widget.Toast

/**
 * Created by BrainyBeam on 05-Jan-19.
 */
object Utility {

    fun isInternetConnected(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    fun showToastMessage(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

}