package com.application.bank.ui.login

import com.application.bank.ui.base.BaseNavigator

interface LoginNavigator : BaseNavigator {
    fun loginResponse(response: String)
    fun loginSuccess()
    fun loginError()
}