package com.example.flashcards.logic.adapters.profile

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.flashcards.data.GroupInfo
import com.example.flashcards.databinding.ItemProfileRecyclerviewGroupBinding
import com.example.flashcards.logic.interfaces.profile.OnGroupClickListener

class ProfileGroupViewHolder(
    binding: ItemProfileRecyclerviewGroupBinding
) : RecyclerView.ViewHolder(binding.root) {

    var groupName: TextView? = binding.groupNameProfile
    var groupAuthor: TextView? = binding.groupAuthor
    var groupFlags: TextView? = binding.groupDate

    fun initializeGroups(item: GroupInfo, action: OnGroupClickListener) {
        itemView.setOnClickListener {
            action.onGroupItemClick(item, absoluteAdapterPosition)
        }
    }
}
