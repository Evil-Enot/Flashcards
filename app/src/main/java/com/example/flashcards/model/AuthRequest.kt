package com.example.flashcards.model

data class AuthRequest(
    val token: Token,
    val login: String?,
    val password: String?
)
