package com.example.flashcards.model

data class UserHistoryResponse(
    val status: Status,
    val records: ArrayList<RecordsHistory>
)
