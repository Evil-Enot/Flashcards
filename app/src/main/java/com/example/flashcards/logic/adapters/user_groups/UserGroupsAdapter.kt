package com.example.flashcards.logic.adapters.user_groups

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.flashcards.model.GroupInfo
import com.example.flashcards.databinding.ItemUserGroupsBinding
import com.example.flashcards.logic.interfaces.user_groups.OnUserGroupsClickListener

class UserGroupsAdapter(
    private val userGroups: ArrayList<GroupInfo>,
    private var clickListener: OnUserGroupsClickListener
) :
    RecyclerView.Adapter<UserGroupsViewHolder>(),
    Filterable {

    var userGroupsList: ArrayList<GroupInfo> = userGroups

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserGroupsViewHolder {
        return UserGroupsViewHolder(
            ItemUserGroupsBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: UserGroupsViewHolder, position: Int) {
        holder.groupName?.text = userGroupsList[position].name
//        holder.groupAuthor?.text = userGroupsList[position].author
//        holder.groupFlags?.text = userGroupsList[position].flags
//        holder.groupMaxPoints?.text = userGroupsList[position].maxPoints
//        holder.groupLastVisit?.text = userGroupsList[position].date

        holder.initializeUserGroups(userGroupsList[position], clickListener)

    }

    override fun getItemCount(): Int {
        return userGroupsList.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                userGroupsList = if (charSearch.isEmpty()) {
                    userGroups
                } else {
                    val resultList = ArrayList<GroupInfo>()
                    for (group in userGroups) {
                        if (group.name.toLowerCase()
                                .contains(charSearch.toLowerCase())
                        ) {
                            resultList.add(group)
                        }
                    }
                    resultList
                }
                val filterResult = FilterResults()
                filterResult.values = userGroupsList
                return filterResult
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                userGroupsList = results?.values as ArrayList<GroupInfo>
                notifyDataSetChanged()
            }
        }
    }
}
