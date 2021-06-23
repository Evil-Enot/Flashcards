package com.example.flashcards.model.history

import java.time.LocalDateTime
import java.util.*

data class HistoryCommit(
    val answer: String,
    val answerTime: Calendar?,
    val isRight: Boolean,
    val qaid: Long
)
