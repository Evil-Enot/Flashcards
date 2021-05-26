package com.example.flashcards.model.history

import com.example.flashcards.model.Status

data class UserHistoryResponse(
    val status: Status,
    val records: ArrayList<RecordsHistory>
)
