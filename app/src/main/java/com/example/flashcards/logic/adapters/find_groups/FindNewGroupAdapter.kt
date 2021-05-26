package com.example.flashcards.logic.adapters.find_groups

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.flashcards.model.GroupInfo
import com.example.flashcards.databinding.ItemAddNewGroupFindBinding
import com.example.flashcards.logic.interfaces.find_groups.OnFindGroupsClickListener

class FindNewGroupAdapter(
    private val groups: ArrayList<GroupInfo>,
    private var clickListener: OnFindGroupsClickListener
) : RecyclerView.Adapter<FindNewGroupViewHolder>(),
    Filterable {

    var groupsList: ArrayList<GroupInfo> = groups

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FindNewGroupViewHolder {
        return FindNewGroupViewHolder(
            ItemAddNewGroupFindBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: FindNewGroupViewHolder, position: Int) {
        holder.groupName?.text = groupsList[position].name
        holder.groupAuthor?.text = groupsList[position].authorUserId.toString()
        holder.groupFlags?.text = groupsList[position].flags
        holder.groupMaxPoints?.text = groupsList[position].maxPoints.toString()

        holder.initializeFindNewGroup(groupsList[position], clickListener)
    }

    override fun getItemCount(): Int {
        return groupsList.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                groupsList = if (charSearch.isEmpty()) {
                    groups
                } else {
                    val resultList = ArrayList<GroupInfo>()
                    for (group in groups) {
                        if (group.name.toLowerCase()
                                .contains(charSearch.toLowerCase())
                        ) {
                            resultList.add(group)
                        }
                    }
                    resultList
                }
                val filterResult = FilterResults()
                filterResult.values = groupsList
                return filterResult
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                groupsList = results?.values as ArrayList<GroupInfo>
                notifyDataSetChanged()
            }
        }
    }
}
