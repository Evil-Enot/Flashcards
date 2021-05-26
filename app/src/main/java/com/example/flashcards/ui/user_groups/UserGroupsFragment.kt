package com.example.flashcards.ui.user_groups

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flashcards.R
import com.example.flashcards.api.WebClient
import com.example.flashcards.databinding.FragmentUserGroupsBinding
import com.example.flashcards.logic.adapters.profile.EmptyGroupsAdapter
import com.example.flashcards.logic.adapters.user_groups.UserGroupsAdapter
import com.example.flashcards.logic.interfaces.user_groups.OnUserGroupsClickListener
import com.example.flashcards.model.RecordsGroup
import com.example.flashcards.model.Token
import com.example.flashcards.model.UserGroupsResponse
import com.example.flashcards.model.UserRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserGroupsFragment : Fragment(), OnUserGroupsClickListener {

    private var userGroupsList: ArrayList<RecordsGroup?> = ArrayList()

    private lateinit var userGroupsViewModel: UserGroupsViewModel
    private var _binding: FragmentUserGroupsBinding? = null

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
        userGroupsViewModel =
            ViewModelProvider(this).get(UserGroupsViewModel::class.java)

        _binding = FragmentUserGroupsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //--------------------------------------------//


        //--------------------------------------------//
        // Получение групп пользователя

        val userGroupsRV: RecyclerView = binding.userGroups

        val userGroups = UserRequest(
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

    // Вывод групп пользователя и фильтрация
    private fun printGroups(userGroupRV: RecyclerView, groupList: ArrayList<RecordsGroup?>) {
        Log.i("test", "success $groupList")

        if (groupList.isEmpty()) {
            userGroupRV.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            userGroupRV.adapter = EmptyGroupsAdapter()
        } else {

            userGroupRV.layoutManager = LinearLayoutManager(context)
            userGroupRV.adapter = UserGroupsAdapter(userGroupsList, this, context)

            val adapter = UserGroupsAdapter(userGroupsList, this, context)
            binding.groupFilter.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    binding.groupFilter.clearFocus()
                    return false
                }

                override fun onQueryTextChange(newGroup: String?): Boolean {
                    adapter.filter.filter(newGroup)
                    return false
                }
            })

            userGroupRV.adapter = adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.addFriend.setOnClickListener {
            findNavController().navigate(R.id.action_to_user_groups_to_addNewGroupPageFragment)
        }
    }

    override fun onUserGroupsItemClick(item: RecordsGroup?, position: Int) {
        findNavController().navigate(R.id.action_to_user_groups_to_groupPageFragment)
    }
}
