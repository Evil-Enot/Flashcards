package com.example.flashcards.model.history

import com.example.flashcards.model.Status
import com.example.flashcards.model.groups.RecordsGroup

data class UserGroupsResponse(
    val status: Status,
    val records: ArrayList<RecordsGroup>
)
