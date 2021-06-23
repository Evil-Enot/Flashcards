package com.example.flashcards.logic.interfaces.profile

import com.example.flashcards.model.groups.RecordsGroup

interface OnGroupClickListener {
    fun onGroupItemClick(item: RecordsGroup?, position: Int)
}
