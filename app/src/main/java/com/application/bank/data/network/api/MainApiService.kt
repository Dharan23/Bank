package com.application.bank.data.network.api

import com.application.bank.data.network.response.StatementList
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path

interface MainApiService {

    @GET("statements/{id}")
    fun getAllStatements(
        @Path("id") id: Int
    ): Deferred<StatementList>

    companion object {
        operator fun invoke(apiService: ApiService): MainApiService {
            return ApiServiceImpl.getRetrofitInstance().create(MainApiService::class.java)
        }
    }
}