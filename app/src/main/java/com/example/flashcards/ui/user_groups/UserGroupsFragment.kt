package com.example.flashcards.ui.user_groups

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
import com.example.flashcards.model.GroupInfo
import com.example.flashcards.databinding.FragmentUserGroupsBinding
import com.example.flashcards.logic.adapters.user_groups.UserGroupsAdapter
import com.example.flashcards.logic.interfaces.user_groups.OnUserGroupsClickListener

class UserGroupsFragment : Fragment(), OnUserGroupsClickListener {

    private var userGroupsList: ArrayList<GroupInfo> = ArrayList()

    private lateinit var userGroupsViewModel: UserGroupsViewModel
    private var _binding: FragmentUserGroupsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        userGroupsViewModel =
            ViewModelProvider(this).get(UserGroupsViewModel::class.java)

        _binding = FragmentUserGroupsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        addUserGroups()

        val userGroups: RecyclerView = binding.userGroups
        userGroups.layoutManager = LinearLayoutManager(userGroups.context)
        userGroups.adapter = UserGroupsAdapter(userGroupsList, this)

        val adapter = UserGroupsAdapter(userGroupsList, this)
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

        userGroups.adapter = adapter

        return root
    }

    private fun addUserGroups() {
//        userGroupsList.add(GroupInfo("First", "Me", "12/03/2020", "wow", "123"))
//        userGroupsList.add(GroupInfo("Second", "He", "10/09/2017", "new", "2355"))
//        userGroupsList.add(GroupInfo("Third", "She", "21/12/2008", "lol", "644357"))
//        userGroupsList.add(GroupInfo("Fourth", "We", "16/01/2066", "kek", "543235"))
//        userGroupsList.add(GroupInfo("Fifth", "They", "22/07/2345", "good", "87986"))
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

    override fun onUserGroupsItemClick(item: GroupInfo, position: Int) {
        findNavController().navigate(R.id.action_to_user_groups_to_groupPageFragment)
    }
}
