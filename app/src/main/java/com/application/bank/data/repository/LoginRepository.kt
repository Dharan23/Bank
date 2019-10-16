package com.application.bank.data.repository

import androidx.lifecycle.LiveData
import com.application.bank.data.network.model.Login
import com.application.bank.data.network.response.LoginResponse

interface LoginRepository {
    val loginResponse: LiveData<LoginResponse>
    suspend fun loginUser(login: Login): LiveData<LoginResponse>
}