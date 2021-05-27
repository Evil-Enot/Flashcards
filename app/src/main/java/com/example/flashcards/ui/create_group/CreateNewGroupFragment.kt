package com.example.flashcards.ui.create_group

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flashcards.MainActivity
import com.example.flashcards.api.WebClient
import com.example.flashcards.databinding.FragmentAddGroupCreateBinding
import com.example.flashcards.databinding.FragmentAddNewGroupCreateQaBinding
import com.example.flashcards.logic.adapters.add_groups.CreateNewGroupAdapter
import com.example.flashcards.model.Token
import com.example.flashcards.model.groups_add.AddCard
import com.example.flashcards.model.groups_add.AddGroup
import com.example.flashcards.model.groups_add.AddNewGroupRequest
import com.example.flashcards.model.groups_add.AddNewGroupResponse
import com.example.flashcards.model.groups_find.AddGroupRequest
import com.example.flashcards.model.groups_find.AddGroupStatus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.random.Random

class CreateNewGroupFragment : Fragment() {

    private var qaList: ArrayList<AddCard> = ArrayList()
    private lateinit var adapter: CreateNewGroupAdapter

    private lateinit var createNewGroupViewModel: CreateNewGroupViewModel
    private var _bindingAddGroup: FragmentAddGroupCreateBinding? = null

    private val webClient = WebClient().getApi()

    private val bindingAddGroup get() = _bindingAddGroup!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //--------------------------------------------//
        // Получение токена и id пользователя
        val userIdSave = context?.getSharedPreferences("UserId", Context.MODE_PRIVATE)
        val userTokenSave = context?.getSharedPreferences("UserToken", Context.MODE_PRIVATE)
        var userToken = ""
        var userId = ""

        if (userTokenSave?.contains("UserToken") == true) {
            userToken = userTokenSave.getString("UserToken", "").toString()
        }
        if (userIdSave?.contains("UserId") == true) {
            userId = userIdSave.getString("UserId", "").toString()
        }
        //--------------------------------------------//

        //--------------------------------------------//
        // Получение общих данных и т.д.
        createNewGroupViewModel =
            ViewModelProvider(this).get(CreateNewGroupViewModel::class.java)

        _bindingAddGroup = FragmentAddGroupCreateBinding.inflate(inflater, container, false)
        val root: View = bindingAddGroup.root
        //--------------------------------------------//

        //--------------------------------------------//
        // Получение данных группы
        val groupName = bindingAddGroup.groupName.text
        val groupMaxPoints = bindingAddGroup.groupMaxPoints.text
        val groupTags = bindingAddGroup.groupFlags.text
        val groupVisibility = Random.nextLong(0, 100)
        val groupIsLeaf = Random.nextBoolean()
        //--------------------------------------------//


        val groupQuestionsRV: RecyclerView = bindingAddGroup.groupQuestionsContainer
        groupQuestionsRV.layoutManager = LinearLayoutManager(context)
        adapter = CreateNewGroupAdapter(context, qaList)
        groupQuestionsRV.adapter = adapter

        val addBtn = bindingAddGroup.addNewQuestion
        addBtn.setOnClickListener { addQA() }

        //--------------------------------------------//
        // Обработка нажатия кнопки submit
        val submitBtn = bindingAddGroup.submitGroup
        submitBtn.setOnClickListener {
            if (groupName.isNotEmpty() && groupMaxPoints.isNotEmpty() && groupTags.isNotEmpty() && qaList.size != 0) {
                val getUser = Token(
                    userId.toLong(),
                    userToken
                )

                val getGroupInfo = AddGroup(
                    groupName.toString(),
                    null,
                    groupMaxPoints.toString().toLong(),
                    groupVisibility,
                    groupTags.toString(),
                    groupIsLeaf,
                    userId.toLong()
                )

                val addGroup = AddNewGroupRequest(
                    getUser,
                    getGroupInfo,
                    qaList.toList()
                )

                saveGroup(addGroup)
            } else {
                if (groupName.isEmpty() && groupMaxPoints.isEmpty() && groupTags.isEmpty()) {
                    Toast.makeText(context, "Fields must be filled", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(
                        context,
                        "The group must have at least one card",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        //--------------------------------------------//

        return root
    }

    // Метод для добавления карточки в групу
    private fun addQA() {
        val newGroupCreate =
            FragmentAddNewGroupCreateQaBinding.inflate(LayoutInflater.from(context))

        val itemQuestion = newGroupCreate.qaQuestion
        val itemAnswer = newGroupCreate.qaAnswer
        val itemTags = newGroupCreate.qaTag
        val addDialog = AlertDialog.Builder(context)

        addDialog.setView(newGroupCreate.root)
        addDialog.setPositiveButton("Add") { dialog, _ ->
            val question = itemQuestion.text.toString()
            val answer = itemAnswer.text.toString()
            val tags = itemTags.text.toString()
            val map = mutableMapOf<String, String>()
            map["question"] = question
            map["answer"] = answer
            val mapArray = ArrayList<Map<String, String>>()
            mapArray.add(map)
            if (question.isNotEmpty() && answer.isNotEmpty() && tags.isNotEmpty()) {
                qaList.add(AddCard(Random.nextLong(100), Random.nextLong(100), tags, map))
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

    // Метод для сохранения и добавления группы
    private fun saveGroup(addGroup: AddNewGroupRequest) {
        //--------------------------------------------//
        // Получение токена и id пользователя
        val userIdSave = context?.getSharedPreferences("UserId", Context.MODE_PRIVATE)
        val userTokenSave = context?.getSharedPreferences("UserToken", Context.MODE_PRIVATE)
        var userToken = ""
        var userId = ""

        if (userTokenSave?.contains("UserToken") == true) {
            userToken = userTokenSave.getString("UserToken", "").toString()
        }
        if (userIdSave?.contains("UserId") == true) {
            userId = userIdSave.getString("UserId", "").toString()
        }
        //--------------------------------------------//

        val callAddGroup = webClient.addNewGroup(addGroup)
        callAddGroup.enqueue(object : Callback<AddNewGroupResponse> {
            override fun onResponse(
                call: Call<AddNewGroupResponse>,
                response: Response<AddNewGroupResponse>
            ) {
                if (response.body()?.status!!.success) {
                    Toast.makeText(context, "Group saved", Toast.LENGTH_SHORT).show()

                    val addNewGroup = AddGroupRequest(
                        Token(userId.toLong(), userToken),
                        response.body()?.group!!.id
                    )

                    val callAddGroup = webClient.addFindGroup(addNewGroup)
                    callAddGroup.enqueue(object : Callback<AddGroupStatus> {
                        override fun onResponse(
                            call: Call<AddGroupStatus>,
                            response: Response<AddGroupStatus>
                        ) {
                            Log.i("test", response.body().toString())
                            if (response.body()?.status!!.success) {
                                Toast.makeText(context, "Group added", Toast.LENGTH_SHORT).show()
                                startApp()
                            } else {
                                Toast.makeText(
                                    context,
                                    response.body()?.status?.message,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                        override fun onFailure(call: Call<AddGroupStatus>, t: Throwable) {
                            Log.i("test", "error $t")
                        }
                    })
                } else {
                    Toast.makeText(context, response.body()?.status!!.message, Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(call: Call<AddNewGroupResponse>, t: Throwable) {
                Log.i("test", "error $t")
            }
        })
    }

    private fun startApp() {
        val intent = Intent(context, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _bindingAddGroup = null
    }
}
