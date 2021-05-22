package com.example.flashcards.logic.adapters.main

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.flashcards.data.GroupInfo
import com.example.flashcards.databinding.ItemUserGroupsBinding
import com.example.flashcards.logic.interfaces.main.OnMainClickListener

class MainViewHolder(
    binding: ItemUserGroupsBinding
) : RecyclerView.ViewHolder(binding.root) {

    var groupName: TextView? = binding.groupNameItem
    var groupAuthor: TextView? = binding.groupAuthorItem
    var groupFlags: TextView? = binding.groupFlagsItem
    var groupMaxPoints: TextView? = binding.groupMaxPointsItem
    var groupLastVisit: TextView? = binding.groupLastVisitItem

    fun initializeUserGroups(item: GroupInfo, action: OnMainClickListener) {
        itemView.setOnClickListener {
            action.onMainItemClick(item, absoluteAdapterPosition)
        }
    }
}