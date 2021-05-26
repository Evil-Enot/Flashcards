package com.example.flashcards.model

data class UserGroupsResponse(
    val status: Status,
    val records: ArrayList<RecordsGroup>
)
