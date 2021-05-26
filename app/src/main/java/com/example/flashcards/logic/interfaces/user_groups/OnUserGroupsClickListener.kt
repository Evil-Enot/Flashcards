package com.example.flashcards.logic.interfaces.user_groups

import com.example.flashcards.model.GroupInfo
import com.example.flashcards.model.RecordsGroup

interface OnUserGroupsClickListener {
    fun onUserGroupsItemClick(item: RecordsGroup?, position: Int)
}
