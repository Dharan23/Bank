package com.application.bank.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.application.bank.data.network.api.LoginApiService
import com.application.bank.data.network.dao.UserDao
import com.application.bank.data.network.model.Login
import com.application.bank.data.network.response.LoginResponse
import com.application.bank.data.network.response.UserAccount
import com.application.bank.util.NoConnectivityException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginRepositoryImpl(
    private val loginApiService: LoginApiService,
    private val userDao: UserDao
) : LoginRepository {

    private val _loginResponse = MutableLiveData<LoginResponse>()
    override val loginResponse: LiveData<LoginResponse>
        get() = _loginResponse

    override suspend fun loginUser(login: Login): LiveData<LoginResponse> {
        try {
            return withContext(Dispatchers.IO) {
                val response = loginApiService.login(login.user, login.password).await()
                if (response.error.code == 0) {
                    persistUserData(response.userAccount)
                }
                _loginResponse.postValue(response)
                return@withContext loginResponse
            }
        } catch (e: NoConnectivityException) {
            throw NoConnectivityException()
        }
    }

    private fun persistUserData(user: UserAccount) {
        GlobalScope.launch(Dispatchers.IO) {
            userDao.upsert(user = user)
        }
    }
}
