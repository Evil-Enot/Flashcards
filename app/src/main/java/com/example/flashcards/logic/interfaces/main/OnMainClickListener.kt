package com.example.flashcards.logic.interfaces.main

import com.example.flashcards.model.RecordsGroup

interface OnMainClickListener {
    fun onMainItemClick(item: RecordsGroup?, position: Int)
}
