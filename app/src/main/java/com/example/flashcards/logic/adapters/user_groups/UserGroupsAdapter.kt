package com.example.flashcards.logic.adapters.user_groups

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.flashcards.data.GroupInfo
import com.example.flashcards.databinding.UserGroupsRecyclerviewCardBinding
import com.example.flashcards.logic.interfaces.user_groups.OnUserGroupsClickListener
import java.util.*
import kotlin.collections.ArrayList

class UserGroupsAdapter(
    private val userGroups: ArrayList<GroupInfo>,
    private var clickListener: OnUserGroupsClickListener
) :
    RecyclerView.Adapter<UserGroupsViewHolder>()
//    Filterable
    {

    var userGroupsList: ArrayList<GroupInfo> = userGroups

//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = UserGroupsViewHolder(
//        UserGroupsRecyclerviewCardBinding.inflate(
//            LayoutInflater.from(parent.context),
//            parent,
//            false
//        )
//    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserGroupsViewHolder {
        return UserGroupsViewHolder(
            UserGroupsRecyclerviewCardBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: UserGroupsViewHolder, position: Int) {
        holder.groupName?.text = userGroups[position].name
        holder.groupAuthor?.text = userGroups[position].author
        holder.groupFlags?.text = userGroups[position].flags
        holder.groupMaxPoints?.text = userGroups[position].maxPoints
        holder.groupLastVisit?.text = userGroups[position].date

        holder.initializeUserGroups(userGroupsList[position], clickListener)

    }

    override fun getItemCount(): Int {
        return userGroupsList.size
    }

//    override fun getFilter(): Filter {
//        return object : Filter() {
//            override fun performFiltering(constraint: CharSequence?): FilterResults {
//                val charSearch = constraint.toString()
//                userGroupsList = if (charSearch.isEmpty()) {
//                    userGroups
//                } else {
//                    val resultList = ArrayList<GroupInfo>()
//                    for (group in userGroups) {
//                        if (group.name.toLowerCase()
//                                .contains(charSearch.toLowerCase())
//                        ) {
//                            resultList.add(group)
//                        }
//                    }
//                    resultList
//                }
//                val filterResult = FilterResults()
//                filterResult.values = userGroupsList
//                return filterResult
//            }
//
//            @Suppress("UNCHECKED_CAST")
//            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
//                userGroupsList = results?.values as ArrayList<GroupInfo>
//                notifyDataSetChanged()
//            }
//        }
//    }
}