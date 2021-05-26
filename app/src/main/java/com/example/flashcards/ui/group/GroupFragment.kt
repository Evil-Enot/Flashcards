package com.example.flashcards.ui.group

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.flashcards.R
import com.example.flashcards.api.WebClient
import com.example.flashcards.databinding.FragmentGroupBinding
import com.example.flashcards.model.groups.GroupResponse
import com.example.flashcards.model.Token
import com.example.flashcards.model.user.UserInfoResponse
import com.example.flashcards.model.user.UserRequest
import com.example.flashcards.ui.group_card.GroupCardFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GroupFragment : Fragment() {
    private lateinit var groupViewModel: GroupViewModel
    private var _binding: FragmentGroupBinding? = null

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
        // Получение id группы и id и токена пользователя
        val userIdSave = context?.getSharedPreferences("UserId", Context.MODE_PRIVATE)
        val userTokenSave = context?.getSharedPreferences("UserToken", Context.MODE_PRIVATE)
        val groupIdSave = context?.getSharedPreferences("GroupId", Context.MODE_PRIVATE)
        var userToken = ""
        var userId = ""
        var groupId = ""

        if (userTokenSave?.contains("UserToken") == true) {
            userToken = userTokenSave.getString("UserToken", "").toString()
        }
        if (userIdSave?.contains("UserId") == true) {
            userId = userIdSave.getString("UserId", "").toString()
        }
        if (groupIdSave?.contains("GroupId") == true) {
            groupId = groupIdSave.getString("GroupId", "").toString()
        }
        //--------------------------------------------//

        //--------------------------------------------//
        // Получение общих данных и т.д.
        groupViewModel =
            ViewModelProvider(this).get(GroupViewModel::class.java)

        _binding = FragmentGroupBinding.inflate(inflater, container, false)
        val root: View = binding.root
        //--------------------------------------------//


        //--------------------------------------------//
        // Получение данных пользователя
        val groupName: TextView = binding.groupTitle
        val groupAuthor: TextView = binding.groupAuthor

        val getGroup = UserRequest(
            Token(userId.toLong(), userToken),
            groupId.toLong()
        )

        val callGroup = webClient.getGroupInfo(getGroup)

        callGroup.enqueue(object : Callback<GroupResponse> {
            override fun onResponse(call: Call<GroupResponse>, response: Response<GroupResponse>) {

                // Получение имени группы
                groupName.text = response.body()?.group?.name.toString()

                // Получение автора группы
                if (response.body()?.group?.authorUserId.toString() != "0") {
                    val getUser = UserRequest(
                        Token(userId.toLong(), userToken),
                        response.body()?.group?.authorUserId!!
                    )

                    val callUser = webClient.getUser(getUser)

                    callUser.enqueue(object : Callback<UserInfoResponse> {
                        override fun onResponse(
                            call: Call<UserInfoResponse>,
                            response: Response<UserInfoResponse>
                        ) {
                            groupAuthor.text = "Author: User " + response.body()?.user?.name
                        }

                        override fun onFailure(call: Call<UserInfoResponse>, t: Throwable) {
                            Log.i("test", "error $t")
                        }
                    })
                } else {
                    groupAuthor.text =
                        "Author: Community " + response.body()?.group?.authorCommunityId.toString()
                }
            }

            override fun onFailure(call: Call<GroupResponse>, t: Throwable) {
                Log.i("test", "error $t")
            }
        })
        //--------------------------------------------//

        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.groups_card, GroupCardFragment())
            ?.commit()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.groupCallbackButton.setOnClickListener {
            findNavController().navigate(R.id.action_groupPageFragment_to_to_callback)
        }
    }
}

