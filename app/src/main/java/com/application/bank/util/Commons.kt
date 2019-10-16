package com.application.bank.util

import android.util.Patterns
import java.util.regex.Matcher
import java.util.regex.Pattern


object Commons {

    fun isEmailValid(email: String): Boolean {
        return email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isPasswordValid(password: String): Boolean {
        val pattern: Pattern
        val matcher: Matcher
        val exp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@\$%^&*-]).{8,}\$"
        pattern = Pattern.compile(exp)
        matcher = pattern.matcher(password)
        print(matcher.matches())
        return matcher.matches()
    }
}