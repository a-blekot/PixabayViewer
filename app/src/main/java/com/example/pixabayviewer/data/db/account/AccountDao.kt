package com.example.pixabayviewer.data.db.account

import androidx.room.Dao
import androidx.room.Query
import com.example.pixabayviewer.data.db.BaseDao

@Dao
abstract class AccountDao : BaseDao<Account> {
    @Query("SELECT * FROM account WHERE email = :email")
    abstract suspend fun fetch(email: String): List<Account>
}
