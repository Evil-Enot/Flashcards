package com.example.flashcards.logic.interfaces.history

import com.example.flashcards.model.history.RecordsHistory

interface OnHistoryClickListener {
    fun onHistoryItemClick(item: RecordsHistory?, position: Int)
}
