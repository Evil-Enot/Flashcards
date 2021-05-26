package com.example.flashcards.ui.create_group

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flashcards.model.cards.QAInfo
import com.example.flashcards.databinding.FragmentAddGroupCreateBinding
import com.example.flashcards.databinding.FragmentAddNewGroupCreateQaBinding
import com.example.flashcards.logic.adapters.add_groups.CreateNewGroupAdapter

class CreateNewGroupFragment : Fragment() {

    private var qaList: ArrayList<QAInfo> = ArrayList()
    private lateinit var adapter: CreateNewGroupAdapter

    private lateinit var createNewGroupViewModel: CreateNewGroupViewModel
    private var _bindingAddGroup: FragmentAddGroupCreateBinding? = null

    private val bindingAddGroup get() = _bindingAddGroup!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        createNewGroupViewModel =
            ViewModelProvider(this).get(CreateNewGroupViewModel::class.java)

        _bindingAddGroup = FragmentAddGroupCreateBinding.inflate(inflater, container, false)
        val root: View = bindingAddGroup.root

        val groupQuestionsRV: RecyclerView = bindingAddGroup.groupQuestionsContainer
        groupQuestionsRV.layoutManager = LinearLayoutManager(context)
        adapter = CreateNewGroupAdapter(context, qaList)
        groupQuestionsRV.adapter = adapter

        val addBtn = bindingAddGroup.addNewQuestion
        addBtn.setOnClickListener { addQA() }

        val submitBtn = bindingAddGroup.submitGroup
        submitBtn.setOnClickListener { saveGroup() }

        return root
    }

    private fun addQA() {
        val newGroupCreate =
            FragmentAddNewGroupCreateQaBinding.inflate(LayoutInflater.from(context))

        val itemQuestion = newGroupCreate.qaQuestion
        val itemAnswer = newGroupCreate.qaAnswer
        val addDialog = AlertDialog.Builder(context)

        addDialog.setView(newGroupCreate.root)
        addDialog.setPositiveButton("Add") { dialog, _ ->
            val question = itemQuestion.text.toString()
            val answer = itemAnswer.text.toString()
            if (question.isNotEmpty() && answer.isNotEmpty()) {
                qaList.add(QAInfo(question, answer))
                adapter.notifyDataSetChanged()
                dialog.dismiss()
            } else {
                Toast.makeText(context, "Fields must be filled", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        addDialog.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }
        addDialog.create()
        addDialog.show()
    }

    private fun saveGroup() {
        Toast.makeText(context, "Group saved", Toast.LENGTH_SHORT)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _bindingAddGroup = null
    }
}
