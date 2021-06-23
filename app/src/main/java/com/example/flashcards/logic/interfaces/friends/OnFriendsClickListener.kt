package com.example.flashcards.logic.interfaces.friends

import com.example.flashcards.model.FriendInfo
import java.text.FieldPosition

interface OnFriendsClickListener {
    fun onFriendsItemClick(item: FriendInfo, position: Int)
}
