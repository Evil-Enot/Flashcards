package com.example.flashcards.logic.adapters.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flashcards.data.GroupInfo
import com.example.flashcards.databinding.UserGroupsRecyclerviewCardBinding
import com.example.flashcards.logic.interfaces.main.OnMainClickListener

class MainAdapter(
    private val userGroups: ArrayList<GroupInfo>,
    private var clickListener: OnMainClickListener
) : RecyclerView.Adapter<MainViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            UserGroupsRecyclerviewCardBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.groupName?.text = userGroups[position].name
        holder.groupAuthor?.text = userGroups[position].author
        holder.groupFlags?.text = userGroups[position].flags
        holder.groupMaxPoints?.text = userGroups[position].maxPoints
        holder.groupLastVisit?.text = userGroups[position].date

        holder.initializeUserGroups(userGroups[position], clickListener)
    }

    override fun getItemCount(): Int {
        return userGroups.size
    }
}