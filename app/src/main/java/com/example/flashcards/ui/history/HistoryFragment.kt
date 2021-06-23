package com.example.flashcards.ui.history

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
import com.example.flashcards.databinding.FragmentHistoryBinding
import com.example.flashcards.logic.adapters.history.HistoryAdapter
import com.example.flashcards.logic.interfaces.history.OnHistoryClickListener
import com.example.flashcards.model.history.RecordsHistory
import com.example.flashcards.model.Token
import com.example.flashcards.model.history.UserHistoryResponse
import com.example.flashcards.model.user.UserRequest
import retrofit2.Callback
import retrofit2.Response

class HistoryFragment : Fragment(), OnHistoryClickListener {

    private lateinit var historyViewModel: HistoryViewModel
    private var _binding: FragmentHistoryBinding? = null

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
        val userHistoryList: ArrayList<RecordsHistory?> = ArrayList()

        historyViewModel =
            ViewModelProvider(this).get(HistoryViewModel::class.java)

        _binding = FragmentHistoryBinding.inflate(inflater, container, false)

        val root: View = binding.root
        //--------------------------------------------//

        //--------------------------------------------//
        // Получение истории пользователя
        val userHistoryRV: RecyclerView = binding.userHistoryContainer

        val userHistory = UserRequest(
            Token(userId.toLong(), userToken),
            userId.toLong(),
        )

        val callUserHistory = webClient.getUserHistory(userHistory)
        callUserHistory.enqueue(object : Callback<UserHistoryResponse> {
            override fun onResponse(
                call: retrofit2.Call<UserHistoryResponse>,
                response: Response<UserHistoryResponse>
            ) {
                Log.i("test", response.body()?.records?.size.toString())
                for (i in 0 until response.body()?.records!!.size) {
                    val userHistoryItem = response.body()?.records?.get(i)
                    userHistoryList.add(userHistoryItem)
                }
                printHistory(userHistoryRV, userHistoryList)
            }

            override fun onFailure(call: retrofit2.Call<UserHistoryResponse>, t: Throwable) {
                Log.i("test", "error $t")
            }
        })
        //--------------------------------------------//

        return root
    }

    // Вывод истории пользователя и фильтрация
    private fun printHistory(
        userHistoryRV: RecyclerView,
        userHistoryList: ArrayList<RecordsHistory?>
    ) {
        Log.i("test", "success $userHistoryList")

        userHistoryRV.layoutManager = LinearLayoutManager(userHistoryRV.context)
        userHistoryRV.adapter = HistoryAdapter(userHistoryList, this)

        val adapter = HistoryAdapter(userHistoryList, this)
        binding.filterHistory.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                binding.filterHistory.clearFocus()
                return false
            }

            override fun onQueryTextChange(newHistory: String?): Boolean {
                adapter.filter.filter(newHistory)
                return false
            }
        })

        userHistoryRV.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onHistoryItemClick(item: RecordsHistory?, position: Int) {
        val groupIdSave = context?.getSharedPreferences("GroupId", Context.MODE_PRIVATE)
        groupIdSave?.edit()?.putString("GroupId", item?.groupId?.toString())?.apply()
        findNavController().navigate(R.id.action_to_user_history_to_groupPageFragment)
    }
}
