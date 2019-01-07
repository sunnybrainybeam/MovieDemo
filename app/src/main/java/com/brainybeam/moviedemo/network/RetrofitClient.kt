package com.brainybeam.moviedemo.network

import com.brainybeam.moviedemo.utility.Constant
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by BrainyBeam on 05-Jan-19.
 */
object RetrofitClient {

    private val httpClient = OkHttpClient.Builder()
    private val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private var apiRepository: ApiRepository


    init {
        val gson = GsonBuilder().setLenient().create()
        httpClient.connectTimeout(60, TimeUnit.SECONDS)
        httpClient.readTimeout(60, TimeUnit.SECONDS)
        httpClient.retryOnConnectionFailure(true)
        if (!httpClient.networkInterceptors().contains(loggingInterceptor)) {
        }
        httpClient.addNetworkInterceptor(loggingInterceptor)
        val retrofit = Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(httpClient.build())
            .build()
        apiRepository = retrofit.create(ApiRepository::class.java)
    }

    fun getApiRepository(): ApiRepository {
        return apiRepository
    }

}