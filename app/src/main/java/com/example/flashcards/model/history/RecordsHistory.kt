package com.example.flashcards.model.history

import java.sql.Timestamp

data class RecordsHistory(
    val groupId: Long,
    val groupName: String,
    val userId: Long,
    val QAId: Long,
    val answer: String,
    val answerTime: Timestamp,
    val isRight: Boolean
)
