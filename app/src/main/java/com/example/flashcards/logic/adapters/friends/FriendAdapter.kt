package com.example.flashcards.logic.adapters.friends

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.flashcards.databinding.ItemFriendBinding
import com.example.flashcards.model.FriendInfo

class FriendAdapter(
    private val userFriends: ArrayList<FriendInfo>,
) : RecyclerView.Adapter<FriendViewHolder>(),
    Filterable {

    var userFriendsList: ArrayList<FriendInfo> = userFriends

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
        return FriendViewHolder(
            ItemFriendBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {
        holder.friendName?.text = userFriendsList[position].name
        holder.friendLevel?.text = userFriendsList[position].level
        holder.friendLastAction?.text = userFriendsList[position].lastAction
    }

    override fun getItemCount(): Int {
        return userFriendsList.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                userFriendsList = if (charSearch.isEmpty()) {
                    userFriends
                } else {
                    val resultList = ArrayList<FriendInfo>()
                    for (friend in userFriends) {
                        if (friend.name.toLowerCase()
                                .contains(charSearch.toLowerCase())
                        ) {
                            resultList.add(friend)
                        }
                    }
                    resultList
                }
                val filterResult = FilterResults()
                filterResult.values = userFriendsList
                return filterResult
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                userFriendsList = results?.values as ArrayList<FriendInfo>
                notifyDataSetChanged()
            }
        }
    }
}
