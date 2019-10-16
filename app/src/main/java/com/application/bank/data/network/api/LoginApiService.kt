package com.application.bank.data.network.api

import com.application.bank.data.network.api.ApiServiceImpl.Companion.getRetrofitInstance
import com.application.bank.data.network.response.LoginResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface LoginApiService {

    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("user") user: String,
        @Field("password") password: String
    ): Deferred<LoginResponse>

    companion object {
        operator fun invoke(apiService: ApiService): LoginApiService {
            return getRetrofitInstance().create(LoginApiService::class.java)
        }
    }
}