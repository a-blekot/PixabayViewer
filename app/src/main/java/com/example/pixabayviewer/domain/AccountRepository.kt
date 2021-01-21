package com.example.pixabayviewer.domain

import com.example.pixabayviewer.data.db.account.Account
import com.example.pixabayviewer.utils.Resource

interface AccountRepository {
    val activeAccount: Account

    suspend fun register(account: Account): Resource<Any>

    suspend fun login(email: String, password: String): Resource<Account>
}
