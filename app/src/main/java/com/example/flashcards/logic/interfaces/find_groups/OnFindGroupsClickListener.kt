package com.example.flashcards.logic.interfaces.find_groups

import com.example.flashcards.model.groups.GroupInfo

interface OnFindGroupsClickListener {
    fun onFindGroupsItemClick(item: GroupInfo, position: Int)
}
