package com.example.flashcards.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.flashcards.R

class UserCommunitiesPageFragment: Fragment() {

    private lateinit var addNewCommunity: ImageButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.user_communitites_page, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        addNewCommunity = view.findViewById(R.id.add_new_community)
        addNewCommunity.setOnClickListener {
            findNavController().navigate(R.id.action_userCommunitiesPageFragment_to_addNewCommunityPageFragment)
        }
    }
}