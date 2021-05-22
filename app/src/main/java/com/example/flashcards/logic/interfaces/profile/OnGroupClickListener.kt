package com.example.flashcards.logic.interfaces.profile

import com.example.flashcards.model.GroupInfo

interface OnGroupClickListener {
    fun onGroupItemClick(item: GroupInfo, position: Int)
}
