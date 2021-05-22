package com.example.flashcards.logic.interfaces.history

import com.example.flashcards.model.HistoryInfo

interface OnHistoryClickListener {
    fun onHistoryItemClick(item: HistoryInfo, position: Int)
}
