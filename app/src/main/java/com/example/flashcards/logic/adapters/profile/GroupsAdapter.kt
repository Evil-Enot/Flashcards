package com.example.flashcards.logic.adapters.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flashcards.model.GroupInfo
import com.example.flashcards.databinding.ItemProfileRecyclerviewGroupBinding
import com.example.flashcards.logic.interfaces.profile.OnGroupClickListener

class GroupsAdapter(
    private val groups: List<GroupInfo>,
    private var clickListener: OnGroupClickListener
) :
    RecyclerView.Adapter<ProfileGroupViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ProfileGroupViewHolder(
        ItemProfileRecyclerviewGroupBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ProfileGroupViewHolder, position: Int) {
        // Поменять
        holder.groupName?.text = groups[position].name
        holder.groupAuthor?.text = groups[position].author
        holder.groupFlags?.text = groups[position].date

        holder.initializeGroups(groups[position], clickListener)
    }

    override fun getItemCount(): Int {
        return groups.size
    }
}
