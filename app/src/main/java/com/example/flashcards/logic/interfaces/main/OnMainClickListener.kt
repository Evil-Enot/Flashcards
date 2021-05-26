package com.example.flashcards.logic.interfaces.main

import com.example.flashcards.model.GroupInfo
import com.example.flashcards.model.Records

interface OnMainClickListener {
    fun onMainItemClick(item: Records?, position: Int)
}
