package com.application.bank.data.network.response

data class Statement(
    val title: String,
    val desc: String,
    val date: String,
    val value: Double
)