package com.example.pixabayviewer.data

import com.example.pixabayviewer.BuildConfig
import com.example.pixabayviewer.R
import com.example.pixabayviewer.data.db.account.Account
import com.example.pixabayviewer.data.db.account.AccountDao
import com.example.pixabayviewer.domain.AccountRepository
import com.example.pixabayviewer.utils.Resource
import javax.inject.Inject

@Suppress("TooManyFunctions")
class AccountRepositoryImpl @Inject constructor(
    private val accountDao: AccountDao
) : AccountRepository {

    override lateinit var activeAccount: Account

    override suspend fun register(account: Account): Resource<Any> {
        val accounts = accountDao.fetch(account.email)
        if (accounts.isNotEmpty()) {
            return Resource.error(R.string.login_already_exists)
        }

        accountDao.insert(account)
        return Resource.success(data = null)
    }

    override suspend fun login(email: String, password: String): Resource<Account> {
        val accounts = accountDao.fetch(email)
        if (accounts.isNullOrEmpty()) {
            return Resource.error(R.string.login_not_exists)
        }

        if (accounts.none { it.password == password }) {
            return Resource.error(R.string.login_wrong_password)
        }

        if (BuildConfig.DEBUG && accounts.size != 1) {
            error("Accounts contains more then one entry with email: $email")
        }

        activeAccount = accounts[0]
        return Resource.success(data = activeAccount)
    }
}
