package com.application.bank.data.network.api

import android.util.Log
import com.application.bank.data.network.ConnectivityInterceptor
import com.application.bank.util.CONNECTION_TIMEOUT
import com.application.bank.util.READ_TIMEOUT
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import kotlin.reflect.KClass

const val BASE_URL = "https://bank-app-test.herokuapp.com/api/"

open class ApiServiceImpl : ApiService {

    companion object {
        private lateinit var retrofit: Retrofit

        operator fun invoke(connectivityInterceptor: ConnectivityInterceptor): ApiService {
            val okHttp = OkHttpClient.Builder()
                .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(connectivityInterceptor)
                .build()

            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(CoroutineCallAdapterFactory.invoke())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttp)
                .build()

            return retrofit.create(ApiService::class.java)
        }

        fun getRetrofitInstance(): Retrofit {
            return retrofit
        }
    }
}