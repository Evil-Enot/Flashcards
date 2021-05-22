package com.example.flashcards.logic.interfaces.main

import com.example.flashcards.model.GroupInfo

interface OnMainClickListener {
    fun onMainItemClick(item: GroupInfo, position: Int)
}
