package com.example.flashcards.model.cards

data class Card(
    val id: Long,
    val groupId: Long,
    val complexity: Long,
    val howToProcess: Long,
    val flags: String,
    val params: Map<String, String>
)
