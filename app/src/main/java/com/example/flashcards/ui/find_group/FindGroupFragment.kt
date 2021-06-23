package com.example.flashcards.ui.find_group

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flashcards.api.WebClient
import com.example.flashcards.databinding.FragmentAddGroupFindBinding
import com.example.flashcards.logic.adapters.find_groups.FindNewGroupAdapter
import com.example.flashcards.model.Token
import com.example.flashcards.model.groups.GroupInfo
import com.example.flashcards.model.groups_find.FindGroupsRequest
import com.example.flashcards.model.groups_find.FindGroupsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FindGroupFragment : Fragment() {

    private lateinit var findGroupViewModel: FindGroupViewModel
    private var _binding: FragmentAddGroupFindBinding? = null

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
        findGroupViewModel =
            ViewModelProvider(this).get(FindGroupViewModel::class.java)

        _binding = FragmentAddGroupFindBinding.inflate(inflater, container, false)
        val root: View = binding.root
        //--------------------------------------------//

        //--------------------------------------------//
        // Получение групп
        val findField = binding.findNewGroup.text

        binding.findNewGroupBtn.setOnClickListener {
            val findGroupsRequest = FindGroupsRequest(
                Token(userId.toLong(), userToken),
                findField.toString()
            )

            val findGroups = webClient.findGroups(findGroupsRequest)
            findGroups.enqueue(object : Callback<FindGroupsResponse> {
                override fun onResponse(
                    call: Call<FindGroupsResponse>,
                    response: Response<FindGroupsResponse>
                ) {
                    Log.i("test", response.body().toString())
                    val groups: RecyclerView = binding.findNewGroupRv

                    var groupsList: List<GroupInfo> = response.body()!!.records

                    groups.layoutManager = LinearLayoutManager(groups.context)
                    groups.adapter =
                        FindNewGroupAdapter(groupsList, context)

                }

                override fun onFailure(call: Call<FindGroupsResponse>, t: Throwable) {
                    Log.i("test", "error $t")
                }
            })
        }
        //--------------------------------------------//

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
