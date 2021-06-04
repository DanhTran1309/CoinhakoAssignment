package com.danhtt.assignment.common.remote

import com.danhtt.assignment.common.exception.NoConnectivityException
import com.danhtt.assignment.common.utils.NetworkUtils
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class AssignmentRetrofit(private val networkUtils: NetworkUtils) {

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getOkHttpClientConfig())
            .build()
    }

    fun getClient() = retrofit

    private fun getOkHttpClientConfig(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            .addInterceptor { getNoConnectivityResponse(it) }
            .build()
    }

    private fun getNoConnectivityResponse(chain: Interceptor.Chain): Response {
        if (!networkUtils.isConnected()) {
            throw NoConnectivityException()
        }
        return chain.proceed(chain.request())
    }

    companion object {
        private const val BASE_URL = "https://www.coinhako.com/"
        private const val TIME_OUT = 10L
    }
}
