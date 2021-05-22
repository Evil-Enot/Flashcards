package com.example.flashcards.logic.adapters.user_groups

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.flashcards.data.GroupInfo
import com.example.flashcards.databinding.UserGroupsRecyclerviewCardBinding
import com.example.flashcards.logic.interfaces.user_groups.OnUserGroupsClickListener

class UserGroupsViewHolder(
    binding: UserGroupsRecyclerviewCardBinding
) : RecyclerView.ViewHolder(binding.root) {

    var groupName: TextView? = binding.groupNameItem
    var groupAuthor: TextView? = binding.groupAuthorItem
    var groupFlags: TextView? = binding.groupFlagsItem
    var groupMaxPoints: TextView? = binding.groupMaxPointsItem
    var groupLastVisit: TextView? = binding.groupLastVisitItem

    fun initializeUserGroups(item: GroupInfo, action: OnUserGroupsClickListener) {
        itemView.setOnClickListener {
            action.onUserGroupsItemClick(item, absoluteAdapterPosition)
        }
    }
}