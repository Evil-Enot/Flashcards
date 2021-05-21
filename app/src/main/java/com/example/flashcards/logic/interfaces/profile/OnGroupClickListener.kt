package com.example.flashcards.logic.interfaces.profile

import com.example.flashcards.data.Groups

interface OnGroupClickListener {
    fun onGroupItemClick(item: Groups, position: Int)
}
