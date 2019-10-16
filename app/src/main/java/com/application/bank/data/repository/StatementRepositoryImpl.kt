package com.application.bank.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.application.bank.data.network.api.MainApiService
import com.application.bank.data.network.response.StatementList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class StatementRepositoryImpl(
    private val apiService: MainApiService
) : StatementRepository {

    private val _statementLiveData = MutableLiveData<StatementList>()
    override val statementLiveData: LiveData<StatementList>
        get() = _statementLiveData

    override suspend fun getAllStatements(id: Int): LiveData<StatementList> {
        return withContext(Dispatchers.IO) {
            apiService.getAllStatements(id).await().also {
                _statementLiveData.postValue(it)
            }
            return@withContext statementLiveData
        }
    }
}