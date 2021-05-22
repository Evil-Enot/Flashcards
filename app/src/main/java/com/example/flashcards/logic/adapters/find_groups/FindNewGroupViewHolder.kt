package com.example.flashcards.logic.adapters.find_groups

import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.flashcards.data.GroupInfo
import com.example.flashcards.databinding.ItemAddNewGroupFindBinding
import com.example.flashcards.logic.interfaces.find_groups.OnFindGroupsClickListener

class FindNewGroupViewHolder(
    binding: ItemAddNewGroupFindBinding
) : RecyclerView.ViewHolder(binding.root) {
    var groupName: TextView? = binding.groupNameItem
    var groupAuthor: TextView? = binding.groupAuthorItem
    var groupFlags: TextView? = binding.groupFlagsItem
    var groupMaxPoints: TextView? = binding.groupMaxPointsItem
    var addGroup: ImageView? = binding.addGroup

    fun initializeFindNewGroup(item: GroupInfo, action: OnFindGroupsClickListener) {
        itemView.setOnClickListener {
            action.onFindGroupsItemClick(item, absoluteAdapterPosition)
        }

        addGroup?.setOnClickListener {
            Toast.makeText(itemView.context, "Group added", Toast.LENGTH_SHORT).show()
        }
    }
}
