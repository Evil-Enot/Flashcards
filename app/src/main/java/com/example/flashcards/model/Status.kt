package com.example.flashcards.model

import com.example.flashcards.type.ErrorCodeType

data class Status(
    val success: Boolean,
    val errorCode: ErrorCodeType,
    val message: String
)
