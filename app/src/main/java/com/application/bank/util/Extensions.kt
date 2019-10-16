package com.application.bank.util

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

fun Context.showToastShort(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun View.showSnackBar(msg: String) {
    Snackbar.make(this, msg, Snackbar.LENGTH_LONG)
        .show()
}

fun Window.setLightActionAndNavigationBar() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        statusBarColor = Color.WHITE
        navigationBarColor = Color.WHITE

        val flags = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        decorView.systemUiVisibility = flags

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            decorView.systemUiVisibility =
                flags or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        }
    }
}

fun Window.setLightNavigationBar() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        navigationBarColor = Color.WHITE
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        }
    }
}

fun View.hideKkeyboard() {
    this.let { v ->
        val imm = this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(v.windowToken, 0)
    }
}