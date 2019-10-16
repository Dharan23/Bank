package com.application.bank.data.network.response

import com.google.gson.annotations.SerializedName

data class StatementList(
    @SerializedName("statementList")
    val statementList: List<Statement>
)