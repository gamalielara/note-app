package com.example.noteapplication

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class LoginViewModel : ViewModel() {
    private val username = MutableLiveData<String?>(null)
    private val password = MutableLiveData<String?>(null)

    fun setUsername(data: String) {
        username.postValue(data)
    }

    fun setPassword(data: String) {
        password.postValue(data)
    }

    fun isLogedIn() = username.value != null

    fun getUsername() = username.value

    fun logOutHandler() {
        username.postValue(null)
        password.postValue(null)
    }
}