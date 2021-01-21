package com.example.pixabayviewer.view.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pixabayviewer.R
import com.example.pixabayviewer.data.db.BaseDao.Companion.NO_ID
import com.example.pixabayviewer.data.db.account.Account
import com.example.pixabayviewer.domain.AccountRepository
import com.example.pixabayviewer.utils.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject

class RegisterViewModel @Inject constructor(private val accountRepository: AccountRepository) :
    ViewModel() {

    private val _result: MutableLiveData<Resource<Any>> = MutableLiveData()
    val result: LiveData<Resource<Any>>
        get() = _result

    private val _emailError: MutableLiveData<Boolean> = MutableLiveData(false)
    val emailError: LiveData<Boolean>
        get() = _emailError

    private val _passwordError: MutableLiveData<Boolean> = MutableLiveData(false)
    val passwordError: LiveData<Boolean>
        get() = _passwordError

    private val _ageError: MutableLiveData<Boolean> = MutableLiveData(false)
    val ageError: LiveData<Boolean>
        get() = _ageError

    fun register(email: String, password: String, age: String) {
        if (wrongInput(email, password, age)) {
            return
        }

        viewModelScope.launch {
            try {
                _result.postValue(
                    accountRepository.register(
                        Account(NO_ID, email, password, age.toInt())
                    )
                )
            } catch (e: Exception) {
                _result.postValue(Resource.error(R.string.account_register_error))
            }
        }
    }

    private fun wrongInput(email: String, password: String, age: String): Boolean {
        return !verifyEmail(email) || !verifyPassword(password) || !verifyAge(age)
    }

    private fun verifyEmail(email: String): Boolean {
        return if (email.matches(EMAIL_REGEX)) {
            true
        } else {
            _emailError.value = true
            false
        }
    }

    private fun verifyPassword(password: String): Boolean {
        return if (password.length in PASSWORD_RANGE) {
            true
        } else {
            _passwordError.value = true
            false
        }
    }

    private fun verifyAge(age: String): Boolean {
        return if (age.isNotEmpty() && age.toInt() in AGE_RANGE) {
            true
        } else {
            _ageError.value = true
            false
        }
    }

    fun emailChanged(email: String) {
        if (email.isNotEmpty()) {
            _emailError.value = false
        }
    }

    fun passwordChanged(password: String) {
        if (password.isNotEmpty()) {
            _passwordError.value = false
        }
    }

    fun ageChanged(age: String) {
        if (age.isNotEmpty()) {
            _ageError.value = false
        }
    }

    companion object {
        val EMAIL_REGEX = """^[\w-.]+@([\w-]+\.)+[\w-]{2,4}$""".toRegex()
        val PASSWORD_RANGE = 6..12
        val AGE_RANGE = 18..99
    }
}