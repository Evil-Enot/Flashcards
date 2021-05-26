package com.example.flashcards.logic.interfaces.main

import com.example.flashcards.model.groups.RecordsGroup

interface OnMainClickListener {
    fun onMainItemClick(item: RecordsGroup?, position: Int)
}
