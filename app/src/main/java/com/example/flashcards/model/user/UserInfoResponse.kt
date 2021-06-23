package com.example.flashcards.model.user

import com.example.flashcards.model.Status

data class UserInfoResponse(
    val status: Status,
    val user: UserInfo
)
