package com.example.flashcards.ui.find_group

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
import com.example.flashcards.data.GroupInfo
import com.example.flashcards.databinding.FragmentAddGroupFindBinding
import com.example.flashcards.logic.adapters.find_groups.FindNewGroupAdapter
import com.example.flashcards.logic.adapters.user_groups.UserGroupsAdapter
import com.example.flashcards.logic.interfaces.find_groups.OnFindGroupsClickListener

class FindGroupFragment : Fragment(), OnFindGroupsClickListener {

    private var groupsList: ArrayList<GroupInfo> = ArrayList()

    private lateinit var findGroupViewModel: FindGroupViewModel
    private var _binding: FragmentAddGroupFindBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        findGroupViewModel =
            ViewModelProvider(this).get(FindGroupViewModel::class.java)

        _binding = FragmentAddGroupFindBinding.inflate(inflater, container, false)
        val root: View = binding.root

        addGroups()

        val groups: RecyclerView = binding.findNewGroupRv
        groups.layoutManager = LinearLayoutManager(groups.context)
        groups.adapter = FindNewGroupAdapter(groupsList, this)

        val adapter = FindNewGroupAdapter(groupsList, this)
        binding.findNewGroup.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                binding.findNewGroup.clearFocus()
                return false
            }

            override fun onQueryTextChange(newGroup: String?): Boolean {
                adapter.filter.filter(newGroup)
                return false
            }
        })

        groups.adapter = adapter

        return root
    }

    private fun addGroups() {
        groupsList.add(GroupInfo("First", "Me", "12/03/2020", "wow", "123"))
        groupsList.add(GroupInfo("Second", "He", "10/09/2017", "new", "2355"))
        groupsList.add(GroupInfo("Third", "She", "21/12/2008", "lol", "644357"))
        groupsList.add(GroupInfo("Fourth", "We", "16/01/2066", "kek", "543235"))
        groupsList.add(GroupInfo("Fifth", "They", "22/07/2345", "good", "87986"))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onFindGroupsItemClick(item: GroupInfo, position: Int) {

    }
}