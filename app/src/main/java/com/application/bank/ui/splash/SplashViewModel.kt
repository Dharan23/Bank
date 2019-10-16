package com.application.bank.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.application.bank.data.repository.UserRepository
import com.application.bank.ui.base.BaseViewModel
import com.application.bank.util.LazyDeferred
import kotlinx.coroutines.launch

class SplashViewModel(
    private val userRepository: UserRepository
) : BaseViewModel<SplashNavigator>() {

    val userExist by LazyDeferred {
        userRepository.userLoggedIn()
    }
}