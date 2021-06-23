package com.example.flashcards.model.groups_add

import com.example.flashcards.model.Token

data class AddNewGroupRequest(
    val token: Token,
    val group: AddGroup,
    val cards: List<AddCard>
)
