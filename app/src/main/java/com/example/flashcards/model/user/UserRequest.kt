package com.example.flashcards.model.user

import com.example.flashcards.model.Token

data class UserRequest(
    val token: Token,
    val id: Long
)
