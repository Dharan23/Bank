package com.application.bank.ui.login

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.application.bank.data.network.model.Login
import com.application.bank.data.network.response.LoginResponse
import com.application.bank.data.repository.LoginRepository
import com.application.bank.ui.base.BaseViewModel
import com.application.bank.util.Commons.isEmailEmpty
import com.application.bank.util.Commons.isEmailValid
import com.application.bank.util.Commons.isPasswordEmpty
import com.application.bank.util.Commons.isPasswordValid
import com.application.bank.util.NoConnectivityException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class LoginViewModel(
    private val loginRepository: LoginRepository
) : BaseViewModel<LoginNavigator>() {

    var userName: String = ""
    var password: String = ""
    var loading: ObservableField<Boolean> = ObservableField(false)

    fun onLogin() = viewModelScope.launch {
        var valid = true
        var username_empty = false
        var password_empty = false
        val navigator = navigator?.get()
        setLoading(true)

        if (isEmailEmpty(userName)) {
            username_empty = true
            navigator?.setUsernameError("Usuário empty")
            setLoading(false)
            return@launch
        }

        if (isPasswordEmpty(password)) {
            password_empty = true
            navigator?.setPasswordError("senha empty")
            setLoading(false)
            return@launch
        }

        if (!username_empty && !isEmailValid(userName)) {
            valid = false
            navigator?.setUsernameError("Usuário inválido")
        }

        if (!password_empty && !isPasswordValid(password)) {
            valid = false
            navigator?.setPasswordError("senha inválido")
        }

        if (!valid) {
//            navigator?.loginResponse("Usuário ou senha incorreta")
            setLoading(false)
            return@launch
        }

        try {
            loginRepository.loginUser(Login(userName, password)).value.apply {
                if (this?.error?.code != 0) {
                    this?.error?.message?.let { navigator?.loginResponse(it) }
                    setLoading(false)
                    return@launch
                }
                setLoading(false)
                navigator?.loginSuccess()
            }
        } catch (e: Exception) {
            setLoading(false)
            if (e is NoConnectivityException) {
                navigator?.noInternetConnection()
            } else
                navigator?.loginError()
        }
    }

    private suspend fun setLoading(v: Boolean) = withContext(Dispatchers.Main) { loading.set(v) }
}