package com.example.flashcards.logic.adapters.user_groups

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.flashcards.databinding.ItemUserGroupsBinding
import com.example.flashcards.logic.interfaces.user_groups.OnUserGroupsClickListener
import com.example.flashcards.model.RecordsGroup

class UserGroupsViewHolder(
    binding: ItemUserGroupsBinding
) : RecyclerView.ViewHolder(binding.root) {

    var groupName: TextView? = binding.groupNameItem
    var groupAuthor: TextView? = binding.groupAuthorItem
    var groupFlags: TextView? = binding.groupFlagsItem
    var groupMaxPoints: TextView? = binding.groupMaxPointsItem
    var groupLastVisit: TextView? = binding.groupLastVisitItem

    fun initializeUserGroups(item: RecordsGroup?, action: OnUserGroupsClickListener) {
        itemView.setOnClickListener {
            action.onUserGroupsItemClick(item, absoluteAdapterPosition)
        }
    }
}
