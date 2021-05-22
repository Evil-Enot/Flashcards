package com.example.flashcards.logic.adapters.history

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.flashcards.data.HistoryInfo
import com.example.flashcards.databinding.ItemHistoryBinding
import com.example.flashcards.logic.interfaces.history.OnHistoryClickListener

class HistoryAdapter(
    private val userHistory: ArrayList<HistoryInfo>,
    private var clickListener: OnHistoryClickListener
) : RecyclerView.Adapter<HistoryViewHolder>(),
    Filterable {

    var userHistoryList: ArrayList<HistoryInfo> = userHistory

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        return HistoryViewHolder(
            ItemHistoryBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.groupName?.text = userHistoryList[position].groupName
        holder.groupMaxPoints?.text = userHistoryList[position].groupMaxPoint
        holder.myPoints?.text = userHistoryList[position].myPoints
        holder.lastAnswer?.text = userHistoryList[position].lastAnswer

        holder.initializeHistory(userHistoryList[position], clickListener)
    }

    override fun getItemCount(): Int {
        return userHistoryList.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                userHistoryList = if (charSearch.isEmpty()) {
                    userHistory
                } else {
                    val resultList = ArrayList<HistoryInfo>()
                    for (history in userHistory) {
                        if (history.groupName.toLowerCase()
                                .contains(charSearch.toLowerCase())
                        ) {
                            resultList.add(history)
                        }
                    }
                    resultList
                }
                val filterResult = FilterResults()
                filterResult.values = userHistoryList
                return filterResult
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                userHistoryList = results?.values as ArrayList<HistoryInfo>
                notifyDataSetChanged()
            }
        }
    }
}
