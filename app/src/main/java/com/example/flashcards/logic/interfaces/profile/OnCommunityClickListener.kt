package com.example.flashcards.logic.interfaces.profile

import com.example.flashcards.model.CommunityInfo

interface OnCommunityClickListener {
    fun onCommunityItemClick(item: CommunityInfo, position: Int)
}
