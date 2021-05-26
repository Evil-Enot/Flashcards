package com.example.flashcards.logic.interfaces.profile

import com.example.flashcards.model.Records

interface OnGroupClickListener {
    fun onGroupItemClick(item: Records?, position: Int)
}
