package com.example.flashcards.logic.interfaces.user_groups

import com.example.flashcards.data.GroupInfo

interface OnUserGroupsClickListener {
    fun onUserGroupsItemClick(item: GroupInfo, position: Int)
}