package com.example.flashcards.logic.adapters.profile

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.flashcards.data.Communities
import com.example.flashcards.databinding.ProfileRecyclerviewCommunityItemBinding
import com.example.flashcards.logic.interfaces.profile.OnCommunityClickListener

class ProfileCommunityViewHolder(
    binding: ProfileRecyclerviewCommunityItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    var communityName: TextView? = binding.communityName
    var communityAuthor: TextView? = binding.communityAuthor
    var communitySubscribers: TextView? = binding.communitySubscribers

    fun initializeCommunities(item: Communities, action: OnCommunityClickListener) {
        itemView.setOnClickListener {
            action.onCommunityItemClick(item, absoluteAdapterPosition)
        }
    }
}
