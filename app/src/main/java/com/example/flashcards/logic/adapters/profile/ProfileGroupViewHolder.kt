package com.example.flashcards.logic.adapters.profile

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.flashcards.data.Groups
import com.example.flashcards.databinding.ProfileRecyclerviewGroupItemBinding
import com.example.flashcards.logic.interfaces.profile.OnGroupClickListener

class ProfileGroupViewHolder(
    binding: ProfileRecyclerviewGroupItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    var groupName: TextView? = binding.groupName
    var groupAuthor: TextView? = binding.groupAuthor
    var groupFlags: TextView? = binding.groupDate

    fun initializeGroups(item: Groups, action: OnGroupClickListener) {
        itemView.setOnClickListener {
            action.onGroupItemClick(item, absoluteAdapterPosition)
        }
    }
}
