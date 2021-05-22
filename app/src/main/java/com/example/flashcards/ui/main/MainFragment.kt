package com.example.flashcards.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flashcards.R
import com.example.flashcards.data.GroupInfo
import com.example.flashcards.databinding.FragmentMainBinding
import com.example.flashcards.logic.adapters.main.MainAdapter
import com.example.flashcards.logic.interfaces.main.OnMainClickListener

class MainFragment : Fragment(), OnMainClickListener {

    private var userGroupsList: ArrayList<GroupInfo> = ArrayList()

    private lateinit var mainViewModel: MainViewModel
    private var _binding: FragmentMainBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mainViewModel =
            ViewModelProvider(this).get(MainViewModel::class.java)

        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val root: View = binding.root

        addUserGroups()

        val userGroups: RecyclerView = binding.mainFeed
        userGroups.layoutManager = LinearLayoutManager(userGroups.context)
        userGroups.adapter = MainAdapter(userGroupsList, this)


        return root
    }

    private fun addUserGroups() {
        userGroupsList.add(GroupInfo("First", "Me", "12/03/2020", "wow", "123"))
        userGroupsList.add(GroupInfo("Second", "He", "10/09/2017", "new", "2355"))
        userGroupsList.add(GroupInfo("Third", "She", "21/12/2008", "lol", "644357"))
        userGroupsList.add(GroupInfo("Fourth", "We", "16/01/2066", "kek", "543235"))
        userGroupsList.add(GroupInfo("Fifth", "They", "22/07/2345", "good", "87986"))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onMainClick(item: GroupInfo, position: Int) {
        findNavController().navigate(R.id.action_to_main_to_groupPageFragment)
    }
}
