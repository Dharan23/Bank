package com.application.bank.data.repository

import androidx.lifecycle.LiveData
import com.application.bank.data.network.response.UserAccount

interface UserRepository {
    val isLoggedIn: LiveData<Boolean>
    suspend fun getUserData(): LiveData<UserAccount>
    suspend fun userLoggedIn(): LiveData<Boolean>
    suspend fun logOutUser(): Boolean
}