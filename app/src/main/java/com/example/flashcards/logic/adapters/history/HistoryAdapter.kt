package com.example.flashcards.logic.adapters.history

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.flashcards.databinding.ItemHistoryBinding
import com.example.flashcards.logic.interfaces.history.OnHistoryClickListener
import com.example.flashcards.model.history.RecordsHistory

class HistoryAdapter(
    private val userHistory: ArrayList<RecordsHistory?>,
    private var clickListener: OnHistoryClickListener
) : RecyclerView.Adapter<HistoryViewHolder>(),
    Filterable {

    var userHistoryList: ArrayList<RecordsHistory?> = userHistory

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
        // Получение имени группы
        holder.groupName?.text = userHistoryList[position]?.groupName

        // Получение даты  ответа
        holder.timeAnswer?.text =
            userHistoryList[position]?.answerTime.toString().subSequence(0..18)

        // Получение ответа
        holder.myAnswer?.text = userHistoryList[position]?.answer

        // Получения результата
        holder.result?.text = if (userHistoryList[position]?.isRight == true) {
            "Correct answer"
        } else {
            "Incorrect answer"
        }

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
                    val resultList = ArrayList<RecordsHistory?>()
                    for (history in userHistory) {
                        if (history?.groupName!!.toLowerCase()
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
                userHistoryList = results?.values as ArrayList<RecordsHistory?>
                notifyDataSetChanged()
            }
        }
    }
}
