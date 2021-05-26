package com.example.flashcards.model

import java.sql.Timestamp

data class Points(
    val userId: Long,
    val lastAnswerTime: Timestamp,
    val points: Long,
    val QAId: Long
)
