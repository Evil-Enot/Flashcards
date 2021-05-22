package com.example.flashcards.logic.adapters.add_groups

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.flashcards.data.QAInfo
import com.example.flashcards.databinding.AddNewGroupCreateQaItemBinding

class CreateNewGroupViewHolder(
    val context: Context,
    binding: AddNewGroupCreateQaItemBinding,
    groupQa: ArrayList<QAInfo>
) : RecyclerView.ViewHolder(binding.root) {

    var groupQuestion: TextView = binding.qaQuestionText
    var groupAnswer: TextView = binding.qaAnswerText
    var menu: ImageView = binding.editQaItem
//    private val popupMenu: PopupMenu = PopupMenu(context, groupQa)
//
//    fun bind(item: QAInfo) {
//        menu.setOnClickListener { popupMenu.popupMenu(it, item) }
//    }
}