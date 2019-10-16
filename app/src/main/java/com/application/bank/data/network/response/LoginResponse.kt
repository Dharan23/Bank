package com.application.bank.data.network.response


data class LoginResponse(
    val userAccount: UserAccount,
    val error: ErrorResponse
)