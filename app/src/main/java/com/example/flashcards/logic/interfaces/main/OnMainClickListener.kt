package com.example.flashcards.logic.interfaces.main

import com.example.flashcards.data.GroupInfo

interface OnMainClickListener {
    fun onMainItemClick(item: GroupInfo, position: Int)
}
