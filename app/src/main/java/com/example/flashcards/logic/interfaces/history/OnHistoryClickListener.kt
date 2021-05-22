package com.example.flashcards.logic.interfaces.history

import com.example.flashcards.data.HistoryInfo

interface OnHistoryClickListener {
    fun onHistoryItemClick(item: HistoryInfo, position: Int)
}
