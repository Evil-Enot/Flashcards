package com.example.flashcards.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.flashcards.R

class UserGroupsPageFragment: Fragment() {

    private lateinit var addNewGroupButton: ImageButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View  = inflater.inflate(R.layout.user_groups_page, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        addNewGroupButton=view.findViewById(R.id.add_friend)
        addNewGroupButton.setOnClickListener {
            findNavController().navigate(R.id.action_to_user_groups_to_addNewGroupPageFragment)
        }
    }
}