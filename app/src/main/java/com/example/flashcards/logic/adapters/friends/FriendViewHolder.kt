package com.example.flashcards.logic.adapters.friends

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.flashcards.databinding.ItemFriendBinding

class FriendViewHolder(
    binding: ItemFriendBinding
) : RecyclerView.ViewHolder(binding.root) {

    var friendName: TextView? = binding.friendName
    var friendLevel: TextView? = binding.friendLevel
    var friendLastAction: TextView? = binding.friendLastAction
}
