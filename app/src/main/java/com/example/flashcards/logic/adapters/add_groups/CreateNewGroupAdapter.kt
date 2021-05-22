package com.example.flashcards.logic.adapters.add_groups

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.flashcards.R
import com.example.flashcards.model.QAInfo
import com.example.flashcards.databinding.FragmentAddNewGroupCreateQaBinding
import com.example.flashcards.databinding.ItemAddNewGroupCreateQaBinding

class CreateNewGroupAdapter(
    val context: Context?,
    private var groupQa: ArrayList<QAInfo>
) :
    RecyclerView.Adapter<CreateNewGroupAdapter.CreateNewGroupViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreateNewGroupViewHolder {
        return CreateNewGroupViewHolder(
            ItemAddNewGroupCreateQaBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CreateNewGroupViewHolder, position: Int) {
        holder.groupQuestion.text = groupQa[position].question
        holder.groupAnswer.text = groupQa[position].answer

        holder.bind(groupQa[position])
    }

    override fun getItemCount(): Int {
        return groupQa.size
    }

    inner class CreateNewGroupViewHolder(
        binding: ItemAddNewGroupCreateQaBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        var groupQuestion: TextView = binding.qaQuestionText
        var groupAnswer: TextView = binding.qaAnswerText
        var menu: ImageView = binding.editQaItem

        fun bind(item: QAInfo) {
            menu.setOnClickListener { popupMenu(it, item) }
        }

        private fun popupMenu(view: View, item: QAInfo) {
            val popupMenus = android.widget.PopupMenu(context, view)
            popupMenus.inflate(R.menu.menu_qa)
            popupMenus.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.editQA -> {
                        val binding =
                            FragmentAddNewGroupCreateQaBinding.inflate(LayoutInflater.from(context))
                        val qaQuestion = binding.qaQuestion
                        val qaAnswer = binding.qaAnswer

                        AlertDialog.Builder(context)
                            .setView(binding.root)
                            .setPositiveButton("Ok") { dialog, _ ->
                                item.question = qaQuestion.text.toString()
                                item.answer = qaAnswer.text.toString()
                                notifyDataSetChanged()
                                Toast.makeText(context, "Card edited", Toast.LENGTH_SHORT)
                                    .show()
                                dialog.dismiss()
                            }
                            .setNegativeButton("Cancel") { dialog, _ ->
                                dialog.dismiss()
                            }
                            .create()
                            .show()

                        true

                    }
                    R.id.delete -> {
                        AlertDialog.Builder(context)
                            .setTitle("Delete")
                            .setMessage("You sure?")
                            .setPositiveButton("Yes") { dialog, _ ->
                                groupQa.remove(item)
                                notifyDataSetChanged()
                                Toast.makeText(context, "Card deleted", Toast.LENGTH_SHORT)
                                    .show()
                                dialog.dismiss()
                            }
                            .setNegativeButton("No") { dialog, _ ->
                                dialog.dismiss()
                            }
                            .create()
                            .show()
                        true
                    }
                    else -> true
                }
            }
            popupMenus.show()
            val popup = android.widget.PopupMenu::class.java.getDeclaredField("mPopup")
            popup.isAccessible = true
            val menu = popup.get(popupMenus)
            menu.javaClass.getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                .invoke(menu, true)
        }
    }
}
