package com.example.flashcards.model.registration

data class RegistrationRequest(
    val name: String,
    val email: String,
    val login: String,
    val password: String
)
