package com.example.flashcards.logic.interfaces.profile

import com.example.flashcards.data.Communities

interface OnCommunityClickListener {
    fun onCommunityItemClick(item: Communities, position: Int)
}
