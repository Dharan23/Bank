package com.application.bank.data.network.response

import androidx.room.Entity
import androidx.room.PrimaryKey

const val USER_ROW_ID = 0

@Entity(tableName = "user")
data class UserAccount(
    val userId: Int,
    val name: String,
    val bankAccount: String,
    val agency: String,
    val balance: Float
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = USER_ROW_ID
}