package com.example.flashcards.ui.friends

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
import com.example.flashcards.databinding.FragmentFriendsBinding
import com.example.flashcards.logic.adapters.friends.FriendAdapter
import com.example.flashcards.model.FriendInfo

class FriendsFragment : Fragment() {

    private var userFriendsList: ArrayList<FriendInfo> = ArrayList()

    private lateinit var friendsViewModel: FriendsViewModel
    private var _binding: FragmentFriendsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        friendsViewModel =
            ViewModelProvider(this).get(FriendsViewModel::class.java)

        _binding = FragmentFriendsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        addUserFriend()

        val userFriends: RecyclerView = binding.friendsContainer
        userFriends.layoutManager = LinearLayoutManager(userFriends.context)
        userFriends.adapter = FriendAdapter(userFriendsList)

        val adapter = FriendAdapter(userFriendsList)
        binding.friendsFilter.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                binding.friendsFilter.clearFocus()
                return false
            }

            override fun onQueryTextChange(newFriend: String?): Boolean {
                adapter.filter.filter(newFriend)
                return false
            }
        })

        userFriends.adapter = adapter

        return root
    }

    private fun addUserFriend() {
        userFriendsList.add(FriendInfo("First", "123", "12/03/2020"))
        userFriendsList.add(FriendInfo("Second", "234", "10/09/2017"))
        userFriendsList.add(FriendInfo("Third", "534636", "21/12/2008"))
        userFriendsList.add(FriendInfo("Fourth", "1234", "16/01/2066"))
        userFriendsList.add(FriendInfo("Fifth", "5346", "22/07/2345"))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.addFriend.setOnClickListener {
            findNavController().navigate(R.id.action_to_friends_to_addFriendPageFragment)
        }
    }
}
