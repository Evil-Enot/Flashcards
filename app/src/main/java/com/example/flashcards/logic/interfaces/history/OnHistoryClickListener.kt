package com.example.flashcards.logic.interfaces.history

import com.example.flashcards.data.HistoryInfo
import java.text.FieldPosition

interface OnHistoryClickListener {
    fun onHistoryItemClick(item: HistoryInfo, position: Int)
}