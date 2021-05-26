package com.example.flashcards.logic.adapters.profile

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.flashcards.databinding.ItemProfileRecyclerviewGroupBinding
import com.example.flashcards.logic.interfaces.profile.OnGroupClickListener
import com.example.flashcards.model.Records

class ProfileGroupViewHolder(
    binding: ItemProfileRecyclerviewGroupBinding
) : RecyclerView.ViewHolder(binding.root) {

    var groupName: TextView? = binding.groupNameProfile
    var groupAuthor: TextView? = binding.groupAuthor
    var groupDate: TextView? = binding.groupDate

    fun initializeGroups(item: Records?, action: OnGroupClickListener) {
        itemView.setOnClickListener {
            action.onGroupItemClick(item, absoluteAdapterPosition)
        }
    }
}
