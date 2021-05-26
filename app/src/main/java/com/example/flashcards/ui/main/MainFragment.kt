package com.example.flashcards.ui.main

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flashcards.R
import com.example.flashcards.api.WebClient
import com.example.flashcards.databinding.FragmentMainBinding
import com.example.flashcards.logic.adapters.main.MainAdapter
import com.example.flashcards.logic.adapters.profile.EmptyGroupsAdapter
import com.example.flashcards.logic.interfaces.main.OnMainClickListener
import com.example.flashcards.model.Records
import com.example.flashcards.model.Token
import com.example.flashcards.model.UserGroupsRequest
import com.example.flashcards.model.UserGroupsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainFragment : Fragment(), OnMainClickListener {

    private var userGroupsList: ArrayList<Records?> = ArrayList()

    private lateinit var mainViewModel: MainViewModel
    private var _binding: FragmentMainBinding? = null

    private val webClient = WebClient().getApi()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

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
        mainViewModel =
            ViewModelProvider(this).get(MainViewModel::class.java)

        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //--------------------------------------------//


        //--------------------------------------------//
        // Получение групп пользователя

        val userGroupsRV: RecyclerView = binding.mainFeed

        val userGroups = UserGroupsRequest(
            Token(userId.toLong(), userToken),
            userId.toLong(),
        )

        val callUserGroups = webClient.getUserGroups(userGroups)
        callUserGroups.enqueue(object : Callback<UserGroupsResponse> {
            override fun onResponse(
                call: Call<UserGroupsResponse>,
                response: Response<UserGroupsResponse>
            ) {
                if (response.body()?.records?.size.toString() != "null") {
                    for (i in 0 until response.body()?.records!!.size) {
                        val userGroup = response.body()?.records?.get(i)
                        userGroupsList.add(userGroup)
                    }
                }
                printGroups(userGroupsRV, userGroupsList)
            }

            override fun onFailure(call: Call<UserGroupsResponse>, t: Throwable) {
                Log.i("test", "error $t")
            }
        })

        //--------------------------------------------//

        return root
    }

    // Вывод групп пользователя
    private fun printGroups(userGroupRV: RecyclerView, groupList: ArrayList<Records?>) {
        Log.i("test", "success $groupList")
        if (groupList.isEmpty()) {
            userGroupRV.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            userGroupRV.adapter = EmptyGroupsAdapter()
        } else {
            userGroupRV.layoutManager = LinearLayoutManager(context)
            userGroupRV.adapter = MainAdapter(groupList, this, context)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onMainItemClick(item: Records?, position: Int) {
        findNavController().navigate(R.id.action_to_main_to_groupPageFragment)
    }
}
