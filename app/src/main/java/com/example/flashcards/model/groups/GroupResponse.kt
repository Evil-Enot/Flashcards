package com.example.flashcards.model.groups

import com.example.flashcards.model.Status

data class GroupResponse(
    val status: Status,
    val group: GroupInfo
)
