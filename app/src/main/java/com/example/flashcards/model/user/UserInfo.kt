package com.example.flashcards.model.user

import java.sql.Timestamp

data class UserInfo(
    val id: Long?,
    val name: String?,
    val email: String?,
    val regTimestamp: Timestamp?,
    val status: String?,
    val level: Long?
)
