package com.example.flashcards.logic.interfaces.history

import com.example.flashcards.model.RecordsHistory
import com.example.flashcards.model.UserHistoryResponse

interface OnHistoryClickListener {
    fun onHistoryItemClick(item: RecordsHistory?, position: Int)
}
