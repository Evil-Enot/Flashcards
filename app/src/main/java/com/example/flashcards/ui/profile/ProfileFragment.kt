package com.example.flashcards.ui.profile

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flashcards.R
import com.example.flashcards.api.WebClient
import com.example.flashcards.databinding.FragmentProfileBinding
import com.example.flashcards.logic.adapters.profile.CommunitiesAdapter
import com.example.flashcards.logic.adapters.profile.EmptyGroupsAdapter
import com.example.flashcards.logic.adapters.profile.GroupsAdapter
import com.example.flashcards.logic.interfaces.profile.OnCommunityClickListener
import com.example.flashcards.logic.interfaces.profile.OnGroupClickListener
import com.example.flashcards.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragment : Fragment(), OnGroupClickListener, OnCommunityClickListener {

    private var communityList: ArrayList<CommunityInfo> = ArrayList()

    private lateinit var profileViewModel: ProfileViewModel
    private var _binding: FragmentProfileBinding? = null

    private val webClient = WebClient().getApi()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @RequiresApi(Build.VERSION_CODES.O)
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

        val groupList: ArrayList<Records?> = ArrayList()

        profileViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        val root: View = binding.root

        //--------------------------------------------//

        //--------------------------------------------//
        // Получение данных пользователя

        val userNameField: TextView = binding.userName
        val userEmailField: TextView = binding.userEmail
        val userStatusField: TextView = binding.userStatus
        val userLevelField: TextView = binding.userLevel

        val getUser = UserInfoRequest(
            Token(userId.toLong(), userToken),
            userId.toLong(),
        )

        val callUser = webClient.getUser(getUser)

        callUser.enqueue(object : Callback<UserInfoResponse> {
            override fun onResponse(
                call: Call<UserInfoResponse>,
                response: Response<UserInfoResponse>
            ) {
                val userName = response.body()?.user?.name.toString()
                val userEmail = response.body()?.user?.email
                val userStatus = response.body()?.user?.status
                val userLevel = response.body()?.user?.level

                userNameField.text = userName
                userEmailField.text = userEmail
                if (userStatus.toString().isEmpty()) {
                    userStatusField.text = "Without status"
                } else {
                    userStatusField.text = userStatus
                }
                userLevelField.text = userLevel.toString()
            }

            override fun onFailure(call: Call<UserInfoResponse>, t: Throwable) {
                Log.i("test", "error $t")
            }
        })

        //--------------------------------------------//

        //--------------------------------------------//
        // Получение групп пользователя

        val userGroupRV: RecyclerView = binding.userGroupsRw

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
                Log.i("test", response.body()?.records?.size.toString())
                if (response.body()?.records?.size.toString() != "null") {
                    for (i in 0 until response.body()?.records!!.size) {
                        val userGroup = response.body()?.records?.get(i)
                        groupList.add(userGroup)
                    }
                }
                printGroups(userGroupRV, groupList)
            }

            override fun onFailure(call: Call<UserGroupsResponse>, t: Throwable) {
                Log.i("test", "error $t")
            }
        })

        //--------------------------------------------//

        //--------------------------------------------//
        // Получение сообществ пользователя

        addCommunities()

        val userCommunities: RecyclerView = binding.userCommunitiesRw
        userCommunities.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        userCommunities.adapter = CommunitiesAdapter(communityList, this)

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
            userGroupRV.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            userGroupRV.adapter = GroupsAdapter(groupList, this, context)
        }
    }

    private fun addCommunities() {
        communityList.add(CommunityInfo("Fifth", "They", "132"))
        communityList.add(CommunityInfo("Fourth", "We", "654"))
        communityList.add(CommunityInfo("First", "Me", "79"))
        communityList.add(CommunityInfo("Third", "She", "251655"))
        communityList.add(CommunityInfo("Second", "He", "6651556520"))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onGroupItemClick(item: Records?, position: Int) {
        findNavController().navigate(R.id.action_to_profile_to_groupPageFragment)
//        intent.putExtra("GroupName", item.name)
//        intent.putExtra("GroupAuthor", item.author)
//        intent.putExtra("GroupTime", item.time)
    }

    override fun onCommunityItemClick(item: CommunityInfo, position: Int) {
        findNavController().navigate(R.id.action_to_profile_to_communityFragment)
    }
}
