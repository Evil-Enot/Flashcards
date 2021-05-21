package com.example.flashcards.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.flashcards.data.UserInfo

class ProfileViewModel : ViewModel() {
    private val _userInfo = MutableLiveData<UserInfo>().apply {
       value = UserInfo("My name", "My email", "My status", "My level")
    }
    val userInfo: LiveData<UserInfo> = _userInfo
}
