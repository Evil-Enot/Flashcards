package com.example.flashcards.model.groups_add

data class AddCard(
    val complexity: Long,
    val howToProcess: Long,
    val flags: String,
    val params: Map<String, String>
)
