package com.example.flashcards.model.cards

import com.example.flashcards.model.Status

data class CardsResponse(
    val status: Status,
    val records: ArrayList<Card>
)
