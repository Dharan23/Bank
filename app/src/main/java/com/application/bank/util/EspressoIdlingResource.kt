package com.application.bank.util

import androidx.test.espresso.idling.CountingIdlingResource

class EspressoIdlingResource {

    companion object {
        const val RESOURCE = "RECYCLER"
        var countingIdlingResource: CountingIdlingResource = CountingIdlingResource(RESOURCE)

        fun increment() {
            countingIdlingResource.increment()
        }

        fun decrement() {
            countingIdlingResource.decrement()
        }
    }
}