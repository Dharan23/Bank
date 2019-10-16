package com.application.bank.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.application.bank.data.network.dao.UserDao
import com.application.bank.data.network.response.UserAccount
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepositoryImpl(
    private val userDao: UserDao
) : UserRepository {
    private val _isLoggedIn = MutableLiveData<Boolean>(false)
    override val isLoggedIn: LiveData<Boolean>
        get() = _isLoggedIn

    override suspend fun getUserData(): LiveData<UserAccount> {
        return withContext(Dispatchers.IO) {
            return@withContext userDao.getUserData()
        }
    }

    override suspend fun userLoggedIn(): LiveData<Boolean> {
        return withContext(Dispatchers.IO) {
            val usercount = userDao.getUserCount()
            if (usercount == 1) {
                _isLoggedIn.postValue(true)
                return@withContext isLoggedIn
            }
            return@withContext isLoggedIn
        }
    }

    override suspend fun logOutUser(): Boolean {
        return withContext(Dispatchers.IO) {
            userDao.logOutUser()
            return@withContext true
        }
    }
}