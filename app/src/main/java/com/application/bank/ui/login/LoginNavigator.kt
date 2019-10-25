package com.application.bank.ui.login

import com.application.bank.ui.base.BaseNavigator

interface LoginNavigator : BaseNavigator {
    fun setUsernameError(message: String)
    fun setPasswordError(message: String)
    fun loginResponse(response: String)
    fun loginSuccess()
    fun loginError()
}