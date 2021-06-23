package com.example.flashcards.model.groups_find

import com.example.flashcards.model.Token

data class AddGroupRequest(
    val token: Token,
    val id: Long
)
