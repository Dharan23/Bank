package com.application.bank.data.repository

import androidx.lifecycle.LiveData
import com.application.bank.data.network.response.StatementList

interface StatementRepository {
    val statementLiveData: LiveData<StatementList>
    suspend fun getAllStatements(id: Int): LiveData<StatementList>
}