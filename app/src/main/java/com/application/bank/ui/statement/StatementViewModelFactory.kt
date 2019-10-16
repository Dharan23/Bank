package com.application.bank.ui.statement

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.application.bank.data.repository.StatementRepository
import com.application.bank.data.repository.UserRepository
import java.lang.Exception

class StatementViewModelFactory(
    private val userRepository: UserRepository,
    private val statementRepository: StatementRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StatementViewModel::class.java))
            return StatementViewModel(userRepository, statementRepository) as T
        throw Exception("Unknown View Model class")
    }
}