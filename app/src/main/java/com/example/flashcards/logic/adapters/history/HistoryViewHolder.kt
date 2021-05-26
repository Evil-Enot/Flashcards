package com.example.flashcards.logic.adapters.history

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.flashcards.databinding.ItemHistoryBinding
import com.example.flashcards.logic.interfaces.history.OnHistoryClickListener
import com.example.flashcards.model.history.RecordsHistory

class HistoryViewHolder(
    binding: ItemHistoryBinding
) : RecyclerView.ViewHolder(binding.root) {
    var groupName: TextView? = binding.groupName
    var myAnswer: TextView? = binding.groupMyAnswer
    var timeAnswer: TextView? = binding.answerTime
    var result: TextView? = binding.result

    fun initializeHistory(item: RecordsHistory?, action: OnHistoryClickListener) {
        itemView.setOnClickListener {
            action.onHistoryItemClick(item, absoluteAdapterPosition)
        }
    }
}
