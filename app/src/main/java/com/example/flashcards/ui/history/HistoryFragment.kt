package com.example.flashcards.ui.history

import android.os.Bundle
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
import com.example.flashcards.model.HistoryInfo
import com.example.flashcards.databinding.FragmentHistoryBinding
import com.example.flashcards.logic.adapters.history.HistoryAdapter
import com.example.flashcards.logic.interfaces.history.OnHistoryClickListener

class HistoryFragment : Fragment(), OnHistoryClickListener {

    private var userHistoryList: ArrayList<HistoryInfo> = ArrayList()

    private lateinit var historyViewModel: HistoryViewModel
    private var _binding: FragmentHistoryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        historyViewModel =
            ViewModelProvider(this).get(HistoryViewModel::class.java)

        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        addUserHistory()

        val userHistory: RecyclerView = binding.userHistoryContainer
        userHistory.layoutManager = LinearLayoutManager(userHistory.context)
        userHistory.adapter = HistoryAdapter(userHistoryList, this)

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

        userHistory.adapter = adapter

        return root
    }

    private fun addUserHistory() {
        userHistoryList.add(HistoryInfo("First", "13245", "1234", "12/03/2020"))
        userHistoryList.add(HistoryInfo("Second", "5341", "3253", "10/09/2017"))
        userHistoryList.add(HistoryInfo("Third", "123526", "34135", "21/12/2008"))
        userHistoryList.add(HistoryInfo("Fourth", "12345", "1234", "16/01/2066"))
        userHistoryList.add(HistoryInfo("Fifth", "23", "44", "22/07/2345"))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onHistoryItemClick(item: HistoryInfo, position: Int) {
        findNavController().navigate(R.id.action_to_user_history_to_groupPageFragment)
    }
}
