package com.example.flashcards.model.auth

import com.example.flashcards.model.Token

data class AuthRequest(
    val token: Token,
    val login: String?,
    val password: String?
)
