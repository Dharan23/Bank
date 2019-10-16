package com.application.bank.ui.base

import androidx.lifecycle.ViewModel
import java.lang.ref.WeakReference

open class BaseViewModel<N> : ViewModel() {

    var navigator: WeakReference<N>? = null

    fun setNavigator(nav: N) {
        navigator = WeakReference(nav)
    }
}