package com.example.flashcards.model.auth

import com.example.flashcards.model.Status
import com.example.flashcards.model.Token

data class AuthResponse(
    val status: Status,
    val token: Token
)
