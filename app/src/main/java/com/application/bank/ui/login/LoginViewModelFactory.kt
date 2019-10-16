package com.application.bank.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.application.bank.data.repository.LoginRepository
import java.lang.Exception

class LoginViewModelFactory(
    private val loginRepository: LoginRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java))
            return LoginViewModel(loginRepository) as T
        throw Exception("View Model class not match")
    }
}