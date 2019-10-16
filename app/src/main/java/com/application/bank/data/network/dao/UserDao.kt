package com.application.bank.data.network.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.application.bank.data.network.response.USER_ROW_ID
import com.application.bank.data.network.response.UserAccount

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(user: UserAccount)

    @Query("SELECT COUNT(*) FROM user")
    fun getUserCount(): Int

    @Query("SELECT * FROM user WHERE id = $USER_ROW_ID")
    fun getUserData(): LiveData<UserAccount>

    @Query("DELETE FROM user where id = $USER_ROW_ID")
    fun logOutUser()
}