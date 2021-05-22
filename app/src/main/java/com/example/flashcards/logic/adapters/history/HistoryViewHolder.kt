package com.example.flashcards.logic.adapters.history

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.flashcards.data.HistoryInfo
import com.example.flashcards.databinding.ItemHistoryBinding
import com.example.flashcards.logic.interfaces.history.OnHistoryClickListener

class HistoryViewHolder(
    binding: ItemHistoryBinding
) : RecyclerView.ViewHolder(binding.root) {
    var groupName: TextView? = binding.groupName
    var groupMaxPoints: TextView? = binding.groupMaxPoints
    var myPoints: TextView? = binding.myPoints
    var lastAnswer: TextView? = binding.lastAnswer

    fun initializeHistory(item: HistoryInfo, action: OnHistoryClickListener) {
        itemView.setOnClickListener {
            action.onHistoryItemClick(item, absoluteAdapterPosition)
        }
    }
}
