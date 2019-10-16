package com.application.bank.ui.statement

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.application.bank.data.repository.StatementRepository
import com.application.bank.data.repository.UserRepository
import com.application.bank.ui.base.BaseViewModel
import com.application.bank.util.LazyDeferred
import com.application.bank.util.NoConnectivityException
import kotlinx.coroutines.launch

class StatementViewModel(
    private val userRepository: UserRepository,
    private val statementRepository: StatementRepository
) : BaseViewModel<StatementNavigator>() {

    private lateinit var statementNavigator: StatementNavigator
    var userIdLivedata = MutableLiveData<Int>()

    val userLiveData by LazyDeferred {
        userRepository.getUserData()
    }

    val statements by LazyDeferred {
        statementRepository.getAllStatements(1)
    }

    fun logOut() = viewModelScope.launch {
        statementNavigator = navigator?.get()!!
        val loggedOut = userRepository.logOutUser()
        if (loggedOut) {
            statementNavigator.logOutUser()
        }
    }
}
