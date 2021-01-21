package com.example.pixabayviewer.view.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pixabayviewer.R
import com.example.pixabayviewer.data.db.account.Account
import com.example.pixabayviewer.domain.AccountRepository
import com.example.pixabayviewer.utils.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor(private val accountRepository: AccountRepository) :
    ViewModel() {

    private val _result: MutableLiveData<Resource<Account>> = MutableLiveData()
    val result: LiveData<Resource<Account>>
        get() = _result

    fun login(email: String, password: String) {
        _result.value = Resource.loading()
        viewModelScope.launch {
            try {
                _result.postValue(accountRepository.login(email, password))
            } catch (e: Exception) {
                _result.postValue(Resource.error(R.string.account_login_error))
            }
        }
    }
}