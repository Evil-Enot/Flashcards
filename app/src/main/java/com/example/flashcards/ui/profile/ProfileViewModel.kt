package com.example.flashcards.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileViewModel : ViewModel() {
    private val _userName = MutableLiveData<String>().apply {
        value = "My name"
    }
    val userName: LiveData<String> = _userName

    private val _userEmail = MutableLiveData<String>().apply {
        value = "My email"
    }
    val userEmail: LiveData<String> = _userEmail

    private val _userStatus = MutableLiveData<String>().apply {
        value = "My status"
    }
    val userStatus: LiveData<String> = _userStatus

    private val _userLevel = MutableLiveData<String>().apply {
        value = "My level"
    }
    val userLevel: LiveData<String> = _userLevel
}
