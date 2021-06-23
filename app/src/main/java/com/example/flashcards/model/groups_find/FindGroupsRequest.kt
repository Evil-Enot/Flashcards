package com.example.flashcards.model.groups_find

import com.example.flashcards.model.Token

data class FindGroupsRequest(
    val token: Token,
    val string: String
)
