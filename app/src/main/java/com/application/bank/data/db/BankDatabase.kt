package com.application.bank.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.application.bank.data.network.dao.UserDao
import com.application.bank.data.network.response.UserAccount

const val ROOM_DATABASE = "bank.db"

@Database(entities = [UserAccount::class], version = 1, exportSchema = false)
abstract class BankDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var instance: BankDatabase? = null

        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context.applicationContext).also { instance = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(context, BankDatabase::class.java, ROOM_DATABASE).build()
    }
}