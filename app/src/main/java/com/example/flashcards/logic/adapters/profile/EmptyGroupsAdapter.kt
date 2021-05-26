package com.example.flashcards.logic.adapters.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flashcards.databinding.ItemEmptyBinding

class EmptyGroupsAdapter(
) : RecyclerView.Adapter<EmptyGroupsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = EmptyGroupsViewHolder(
        ItemEmptyBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: EmptyGroupsViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 1
    }
}