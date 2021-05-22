package com.example.flashcards.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.flashcards.R

class FriendsPageFragment: Fragment() {

    private lateinit var addNewFriend: ImageButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.friends_page, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        addNewFriend = view.findViewById(R.id.add_friend)
        addNewFriend.setOnClickListener {
            findNavController().navigate(R.id.action_to_friends_to_addFriendPageFragment)
        }
    }

}