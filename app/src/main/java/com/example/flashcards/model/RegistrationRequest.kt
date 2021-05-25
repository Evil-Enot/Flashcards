package com.example.flashcards.model

data class RegistrationRequest(
    val name: String,
    val email: String,
    val login: String,
    val password: String
)
