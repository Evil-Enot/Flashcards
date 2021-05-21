package com.example.flashcards.logic.interfaces.profile

import com.example.flashcards.data.CommunityInfo

interface OnCommunityClickListener {
    fun onCommunityItemClick(item: CommunityInfo, position: Int)
}
