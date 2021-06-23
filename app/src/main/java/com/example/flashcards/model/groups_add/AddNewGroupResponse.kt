package com.example.flashcards.model.groups_add

import com.example.flashcards.model.Status
import com.example.flashcards.model.groups.GroupInfo

data class AddNewGroupResponse(
    val status: Status,
    val group: GroupInfo
)
