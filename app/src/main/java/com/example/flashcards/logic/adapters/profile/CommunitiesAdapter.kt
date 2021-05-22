package com.example.flashcards.logic.adapters.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flashcards.data.CommunityInfo
import com.example.flashcards.databinding.ItemProfileRecyclerviewCommunityBinding
import com.example.flashcards.logic.interfaces.profile.OnCommunityClickListener

class CommunitiesAdapter(
    private val communities: List<CommunityInfo>,
    private var clickListener: OnCommunityClickListener
) :
    RecyclerView.Adapter<ProfileCommunityViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ProfileCommunityViewHolder(
        ItemProfileRecyclerviewCommunityBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ProfileCommunityViewHolder, position: Int) {
        // Поменять
        holder.communityName?.text = communities[position].name
        holder.communityAuthor?.text = communities[position].author
        holder.communitySubscribers?.text = communities[position].subscribers

        holder.initializeCommunities(communities[position], clickListener)
    }

    override fun getItemCount(): Int {
        return communities.size
    }
}
