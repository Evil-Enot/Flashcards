package com.example.flashcards.model

import java.sql.Timestamp

data class UserInfo(
    val id: Long?,
    val name: String?,
    val email: String?,
    val regTimestamp: Timestamp?,
    val status: String?,
    val level: Long?
)
