package com.example.flashcards.model.history

import com.example.flashcards.model.Token

data class UserHistoryRequest(
    val token: Token,
    val history: List<HistoryCommit>
)
